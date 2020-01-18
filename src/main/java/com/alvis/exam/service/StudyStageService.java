package com.alvis.exam.service;


import com.alvis.exam.domain.StudyStage;
import com.alvis.exam.domain.SubjectType;
import com.alvis.exam.viewmodel.admin.textbook.TextBookPageRequestVM;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface StudyStageService {

    /**
     * 查询所有学段
     *
     * @return
     */
    List<StudyStage> allStudyStage();

    /**
     * 添加学段和学科信息
     *
     * @param model
     * @return
     */
    void insertStudyStageAndSubject(@Valid TextBookPageRequestVM model);

    /**
     * 通过id查询,再修改
     *
     * @param studyStageId
     * @return
     */
    StudyStage getStudyStageById(String studyStageId);

    /**
     * 修改删除字段的状态
     *
     * @param studyStage
     */
    void updateByIdFilter(StudyStage studyStage);

    /**
     * 修改学段
     *
     * @param model
     */

    void updateStudyStage(@Valid TextBookPageRequestVM model);

    /**
     * 学段查询学科
     *
     * @return
     */

    Map<String, Object> StudyStage();

}
