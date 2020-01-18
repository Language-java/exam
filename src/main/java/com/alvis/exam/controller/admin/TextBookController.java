package com.alvis.exam.controller.admin;


import com.alvis.exam.base.BaseApiController;
import com.alvis.exam.base.RestResponse;
import com.alvis.exam.base.SystemCode;
import com.alvis.exam.domain.*;
import com.alvis.exam.service.*;
import com.alvis.exam.utility.*;
import com.alvis.exam.viewmodel.admin.textbook.TextBookPageRequestVM;
import com.alvis.exam.viewmodel.admin.textbook.TextBookResponseVM;
import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * 教材信息管理
 */
@Slf4j
@RestController("AdminTextBookController")
@RequestMapping(value = "/api/admin/textbook")
@AllArgsConstructor
public class TextBookController extends BaseApiController {

    private final StudyStageService studyStageService;  //查询学段信息

    private final SubjectTypeService subjectTypeService; //查询学科信息

    private final ChapterService chapterService; //章节列表

    private final ChapterTypeService chapterTypeService; //类型 (版本,册次,章节)


    /**
     * 加入分页查所有,其他功能另改 ,学段和学科去调用别的接口,暂时没用没用
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/page1", method = RequestMethod.POST)
    public RestResponse<PageInfo<TextBookResponseVM>> pageList1(@RequestBody TextBookPageRequestVM model) {
        PageInfo<Chapter> pageInfo = chapterService.page1(model);
        PageInfo<TextBookResponseVM> page = PageInfoHelper.copyMap(pageInfo, b -> {
            TextBookResponseVM vm = modelMapper.map(b, TextBookResponseVM.class); //响应回去的数据
            return vm; //使用lambda表达式,把返回值作为参数传进去
        });
        return RestResponse.ok(page);
    }


    /**
     * 分页加递归查询 ,还没用该功能
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<Map<String, Object>>> pageList(@RequestBody TextBookPageRequestVM model) {
        PageInfo<Map<String, Object>> pageInfo = chapterService.page(model);
        return RestResponse.ok(pageInfo);
    }


    /**
     * 查询教材中的学段
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/studyStage", method = RequestMethod.POST)
    public RestResponse<List<StudyStage>> studyStage() {
        List<StudyStage> list = studyStageService.allStudyStage();
        return RestResponse.ok(list);
    }


    /**
     * 根据学段studyStageId查询学科 (OK)
     *
     * @return
     */
    @RequestMapping(value = "/subjectType/studyStageId", method = RequestMethod.POST)
    public RestResponse<List<SubjectType>> subjectStudyStageId(@RequestBody TextBookPageRequestVM model) {
        model.setOrderBy("id");
        List<SubjectType> subjectTypes = subjectTypeService.subjectStudyStageId(model);
        return RestResponse.ok(subjectTypes);
    }


    /**
     * 根据学段和学科查询章节列表,返回所有数据前端处理 不用了
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/chapter/list2", method = RequestMethod.POST)
    public RestResponse<List<Chapter>> list(@RequestBody TextBookPageRequestVM model) {
        List<Chapter> list = chapterService.chapterList(model);
        return RestResponse.ok(list);
    }


    /**
     * 递归,根据学段和学科查询章节列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/chapter/list", method = RequestMethod.POST)
    public RestResponse<Map<String, Object>> getTree(@RequestBody TextBookPageRequestVM model) {
        model.setOrderBy("id");
        Map<String, Object> map = chapterService.getTree(model);
        return RestResponse.ok(map);
    }


    /**
     * 查询教材章节的类型(分类,版本,册次,章节,小节等等名称) ,分组和知识点的类型显示也调用这个接口
     * 加入了类型识别type
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/chapterType/list", method = RequestMethod.POST)
    public RestResponse<List<ChapterType>> list() {
        List<ChapterType> list = chapterTypeService.chapterTypeList();
        return RestResponse.ok(list);
    }


    /**
     * 添加数据(教材章节列表)
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public RestResponse<Chapter> insert(@RequestBody @Valid TextBookPageRequestVM model) {
        Chapter chapter = chapterService.insertChapter(model);
        return RestResponse.ok(chapter);
    }


    /**
     * 修改,只修改chapterId对应的name,修改章节列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse<Chapter> update(@RequestBody @Valid TextBookPageRequestVM model) {
        Chapter chapter = chapterService.updateChapter(model);
        return RestResponse.ok(chapter);
    }


    /**
     * 根据它的唯一id(chapterId)删除
     *
     * @param chapterId
     * @return
     */
    @RequestMapping(value = "/delete/{chapterId}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable String chapterId) {
        chapterService.deleteByChapterId(chapterId);
        return RestResponse.ok();
    }


    /**
     * 修改是否启用（Status）状态
     *
     * @param id
     * @return
     */
   /* @RequestMapping(value = "/changeStatus/{ChapterId}", method = RequestMethod.POST)
    public RestResponse<Integer> changeStatus(@PathVariable Integer ChapterId) {
        Chapter chapter = chapterService.getChapterId(id);  //查
        UserStatusEnum.fromCode(chapter.getStatus()); //使用枚举获取它的状态标识
        //查看是否启用,如果不是则开启,否则关闭
        Integer newStatus = userStatusEnum == UserStatusEnum.Enable ? UserStatusEnum.Disable.getCode() : UserStatusEnum.Enable.getCode();
        chapter.setStatus(newStatus);  //设置新的状态
        chapterService.updateByChapterIdFilter(chapter);  //去数据库修改它的状态
        return RestResponse.ok(newStatus); //响应回去
    }*/


}
