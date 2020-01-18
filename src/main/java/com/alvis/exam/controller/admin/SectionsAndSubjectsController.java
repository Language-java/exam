package com.alvis.exam.controller.admin;


import com.alvis.exam.base.BaseApiController;
import com.alvis.exam.base.RestResponse;
import com.alvis.exam.domain.StudyStage;
import com.alvis.exam.domain.SubjectType;
import com.alvis.exam.service.StudyStageService;
import com.alvis.exam.service.SubjectTypeService;
import com.alvis.exam.viewmodel.admin.textbook.TextBookPageRequestVM;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 学段和科目管理
 */
@Slf4j
@RestController("AdminSectionsAndSubjectsController")
@RequestMapping(value = "/api/admin/SectionsAndSubjects")
@AllArgsConstructor
public class SectionsAndSubjectsController extends BaseApiController {

    private final StudyStageService studyStageService;  //查询学段信息

    private final SubjectTypeService subjectTypeService; //查询学科信息


    /**
     * 查询学段和学科,递归,把学科当成父级
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public RestResponse<Map<String, Object>> list() {
        Map<String, Object> objects = studyStageService.StudyStage();//查询学段
        return RestResponse.ok(objects);
    }


    /**
     * 添加学段和学科数据(教材章节列表)
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/insertStudyStageAndSubject", method = RequestMethod.POST)
    public RestResponse<Object> insertStudyStageAndSubject(@RequestBody @Valid TextBookPageRequestVM model) {
        studyStageService.insertStudyStageAndSubject(model);
        return RestResponse.ok();
    }


    /**
     * 根据用户ID更改用户delete字段进行逻辑删除,学段
     *
     * @param studyStageId
     * @return
     */
    @RequestMapping(value = "/deleteStudyStage/{studyStageId}", method = RequestMethod.POST)
    public RestResponse deleteStudyStage(@PathVariable String studyStageId) {
        StudyStage studyStage = studyStageService.getStudyStageById(studyStageId);
        studyStage.setDeleted(true);
        studyStageService.updateByIdFilter(studyStage);
        return RestResponse.ok();
    }


    /**
     * 根据用户ID更改用户delete字段进行逻辑删除,学科
     *
     * @param subjectTypeId
     * @return
     */
    @RequestMapping(value = "/deleteSubjectType/{subjectTypeId}", method = RequestMethod.POST)
    public RestResponse deleteSubjectType(@PathVariable String subjectTypeId) {
        SubjectType subjectType = subjectTypeService.getSubjectTypeById(subjectTypeId);
        subjectType.setDeleted(true);
        subjectTypeService.updateByIdFilter(subjectType);
        return RestResponse.ok();
    }


    /**
     * 修改学段或者学科
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/updateStudyStage", method = RequestMethod.POST)
    public RestResponse<Object> update(@RequestBody @Valid TextBookPageRequestVM model) {
        if (model.getSubjectTypeId() == null || model.getSubjectTypeId().equals("")) {
            studyStageService.updateStudyStage(model); //修改学段
        } else {
            subjectTypeService.updateSubjectType(model); //修改学科
        }
        return RestResponse.ok();
    }
}
