package com.alvis.exam.controller.admin;

import com.alvis.exam.base.BaseApiController;
import com.alvis.exam.base.RestResponse;
import com.alvis.exam.base.SystemCode;
import com.alvis.exam.domain.Question;
import com.alvis.exam.domain.TextContent;
import com.alvis.exam.domain.enums.QuestionTypeEnum;
import com.alvis.exam.domain.question.QuestionObject;
import com.alvis.exam.service.QuestionService;
import com.alvis.exam.service.TextContentService;
import com.alvis.exam.utility.*;
import com.alvis.exam.viewmodel.admin.question.QuestionEditRequestVM;
import com.alvis.exam.viewmodel.admin.question.QuestionPageRequestVM;
import com.alvis.exam.viewmodel.admin.question.QuestionResponseVM;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 试题controller
 */
@RestController("AdminQuestionController")
@RequestMapping(value = "/api/admin/question")
@AllArgsConstructor//构造函数实现接口注入
public class QuestionController extends BaseApiController {

    private final QuestionService questionService;
    private final TextContentService textContentService;


    /**
     * 参数封装都是层层相扣的,分别查询或者设置进去
     * 返回题目列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)  //高级查询条件和分页是一块查的
    public RestResponse<PageInfo<QuestionResponseVM>> pageList(@RequestBody QuestionPageRequestVM model) {
        PageInfo<Question> pageInfo = questionService.page(model);
        PageInfo<QuestionResponseVM> page = PageInfoHelper.copyMap(pageInfo, q -> {  //表达式转换规则
            QuestionResponseVM vm = modelMapper.map(q, QuestionResponseVM.class);  //把相同参数的值QuestionResponseVM映射到q,返回vm
            vm.setCreateTime(DateTimeUtil.dateFormat(q.getCreateTime()));  //把时间和分数设置进去的(需要实时的)
            vm.setScore(ExamUtil.scoreToVM(q.getScore())); //分数 (需要计算的)
            //用试题表中的题干ID查询题干  (预览功能)
            TextContent textContent = textContentService.selectById(q.getInfoTextContentId()); //通过question的id查(查的是subject的数据)
            //使用jsonUtil把题干编辑格式转换成题干对象(不确定)
            QuestionObject questionObject = JsonUtil.toJsonObject(textContent.getContent(), QuestionObject.class);
            String clearHtml = HtmlUtil.clear(questionObject.getTitleContent());
            vm.setShortTitle(clearHtml); //题干 （是试题内容）
            return vm;
        });
        return RestResponse.ok(page);
    }

    /**
     * 编辑存在修改信息，反则保存
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<QuestionEditRequestVM> edit(@RequestBody @Valid QuestionEditRequestVM model) {
        RestResponse validQuestionEditRequestResult = validQuestionEditRequestVM(model);
        //判断数据对比是否正确，不正确抛出异常
        if (validQuestionEditRequestResult.getCode() != SystemCode.OK.getCode()) {
            return validQuestionEditRequestResult;
        }
        Question question;

        if (null == model.getId()) {
            question = questionService.insertFullQuestion(model, getCurrentUser().getId());
        } else {
            question = questionService.updateFullQuestion(model);
        }
        QuestionEditRequestVM newVM = questionService.getQuestionEditRequestVM(question.getId());
        return RestResponse.ok(newVM);
    }

    /**
     * 根据ID查询数据 （预览功能实现）
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<QuestionEditRequestVM> select(@PathVariable Integer id) {
        QuestionEditRequestVM newVM = questionService.getQuestionEditRequestVM(id);
        return RestResponse.ok(newVM);
    }

    /**
     * 根据ID进行逻辑删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {
        Question question = questionService.selectById(id);
        question.setDeleted(true);
        questionService.updateByIdFilter(question);
        return RestResponse.ok();
    }


    private RestResponse validQuestionEditRequestVM(QuestionEditRequestVM model) {
        int qType = model.getQuestionType().intValue();
        //判断题目类型是单选题/多选题
        boolean requireCorrect = qType == QuestionTypeEnum.SingleChoice.getCode() || qType == QuestionTypeEnum.TrueFalse.getCode();
        if (requireCorrect) {
            //判断答案是否为null
            if (StringUtils.isBlank(model.getCorrect())) {
                String errorMsg = ErrorUtil.parameterErrorFormat("correct", "不能为空");
                return new RestResponse<>(SystemCode.ParameterValidError.getCode(), errorMsg);
            }
        }

        //判断是否为填空题
        if (qType == QuestionTypeEnum.GapFilling.getCode()) {
            //遍历题干的分数求和
            Integer fillSumScore = model.getItems().stream().mapToInt(d -> ExamUtil.scoreFromVM(d.getScore())).sum();
            Integer questionScore = ExamUtil.scoreFromVM(model.getScore());
            //判断题目总分数是否和题干上的分数相同
            if (!fillSumScore.equals(questionScore)) {
                String errorMsg = ErrorUtil.parameterErrorFormat("score", "空分数和与题目总分不相等"); //异常处理
                return new RestResponse<>(SystemCode.ParameterValidError.getCode(), errorMsg);
            }
        }
        return RestResponse.ok();
    }
}
