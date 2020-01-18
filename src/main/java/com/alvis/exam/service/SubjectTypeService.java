package com.alvis.exam.service;

import com.alvis.exam.domain.SubjectType;
import com.alvis.exam.viewmodel.admin.textbook.TextBookPageRequestVM;

import javax.validation.Valid;
import java.util.List;

public interface SubjectTypeService {

    /**
     * 根据学段id查询学科
     *
     * @param model
     * @return
     */
    List<SubjectType> subjectStudyStageId(TextBookPageRequestVM model);

    /**
     * 根据学段的id修改
     *
     * @param model
     */
    void updateSubjectType(@Valid TextBookPageRequestVM model);

    /**
     * 根据学科uuid查询删除
     *
     * @param subjectTypeId
     * @return
     */
    SubjectType getSubjectTypeById(String subjectTypeId);

    /**
     * 修改逻辑
     *
     * @param subjectType
     */
    void updateByIdFilter(SubjectType subjectType);
}
