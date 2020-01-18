package com.alvis.exam.controller.admin;

import com.alvis.exam.base.BaseApiController;
import com.alvis.exam.base.RestResponse;
import com.alvis.exam.domain.ExamPaper;
import com.alvis.exam.service.ExamPaperService;
import com.alvis.exam.utility.DateTimeUtil;
import com.alvis.exam.utility.PageInfoHelper;
import com.alvis.exam.viewmodel.admin.exam.ExamPaperPageRequestVM;
import com.alvis.exam.viewmodel.admin.exam.ExamPaperEditRequestVM;
import com.alvis.exam.viewmodel.admin.exam.ExamResponseVM;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 试卷controller
 */
@RestController("AdminExamPaperController")
@RequestMapping(value = "/api/admin/exam/paper")       //调用这里的api,admin是管理后台的前端代码
@AllArgsConstructor
public class ExamPaperController extends BaseApiController {

    private final ExamPaperService examPaperService;

    /**
     * 返回试卷列表
     * 能查询到科目的原因是这边返回的是id,前端收到id做处理,然后根据id异步发一个查询学科和年级的请求查询
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamResponseVM>> pageList(@RequestBody ExamPaperPageRequestVM model) {
        PageInfo<ExamPaper> pageInfo = examPaperService.page(model);
        PageInfo<ExamResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamResponseVM vm = modelMapper.map(e, ExamResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }


    /**
     * 查询task_exam_id字段为null，试卷类型为任务类型的试卷  (这里是有问题的)
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/taskExamPage", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamResponseVM>> taskExamPageList(@RequestBody ExamPaperPageRequestVM model) {
        PageInfo<ExamPaper> pageInfo = examPaperService.taskExamPage(model); //查询不到试卷直接跳过,返回page
        PageInfo<ExamResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> { //有试卷会进入这里,返回page,里面是试卷数据
            ExamResponseVM vm = modelMapper.map(e, ExamResponseVM.class);  //使用的Lambda表达式,返回的是ExamResponseVM的对象
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm; //使用lambda表达式,把返回值作为参数传进去
        });
        return RestResponse.ok(page);
    }


   /* @RequestMapping(value = "/taskExamPage", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamResponseVM>> taskExamPageList(@RequestBody ExamPaperPageRequestVM model) {
        PageInfo<ExamResponseVM> page = null;
        if (model.getTaskExamId() == null) {
            PageInfo<ExamPaper> pageInfo = examPaperService.taskExamPage(model); //查询不到试卷直接跳过,返回page
            page = PageInfoHelper.copyMap(pageInfo, e -> { //有试卷会进入这里,返回page,里面是试卷数据
                ExamResponseVM vm = modelMapper.map(e, ExamResponseVM.class);  //使用的Lambda表达式,返回的是ExamResponseVM的对象
                vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
                return vm; //使用lambda表达式,把返回值作为参数传进去
            });
        } else {
            PageInfo<ExamPaper> pageInfo = examPaperService.page(model);
            page = PageInfoHelper.copyMap(pageInfo, e -> {
                ExamResponseVM vm = modelMapper.map(e, ExamResponseVM.class);
                vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
                return vm;
            });
        }
        return RestResponse.ok(page);
    }*/

    /**
     * 编辑
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> edit(@RequestBody @Valid ExamPaperEditRequestVM model) {
        ExamPaper examPaper = examPaperService.savePaperFromVM(model, getCurrentUser());
        ExamPaperEditRequestVM newVM = examPaperService.examPaperToVM(examPaper.getId());
        return RestResponse.ok(newVM);
    }


    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> select(@PathVariable Integer id) {
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(id);
        return RestResponse.ok(vm);
    }


    /**
     * 根据ID进行逻辑删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {
        ExamPaper examPaper = examPaperService.selectById(id);
        examPaper.setDeleted(true);
        examPaperService.updateByIdFilter(examPaper);
        return RestResponse.ok();
    }
}
