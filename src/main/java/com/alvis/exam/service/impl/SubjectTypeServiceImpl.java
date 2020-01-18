package com.alvis.exam.service.impl;

import com.alvis.exam.domain.StudyStage;
import com.alvis.exam.domain.SubjectType;
import com.alvis.exam.repository.SubjectTypeMapper;
import com.alvis.exam.service.SubjectTypeService;
import com.alvis.exam.viewmodel.admin.textbook.TextBookPageRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by webrx on 2019/12/27.
 */
@Service
public class SubjectTypeServiceImpl implements SubjectTypeService {

    private final SubjectTypeMapper subjectTypeMapper;

    @Autowired
    public SubjectTypeServiceImpl(SubjectTypeMapper subjectTypeMapper) {
        this.subjectTypeMapper = subjectTypeMapper;
    }


    @Override
    public List<SubjectType> subjectStudyStageId(TextBookPageRequestVM model) {

        return subjectTypeMapper.allSubjectStudyStageId(model);
    }

    @Override
    public void updateSubjectType(@Valid TextBookPageRequestVM model) {
        subjectTypeMapper.updateSubjectType(model);
    }

    @Override
    public SubjectType getSubjectTypeById(String subjectTypeId) {

        return subjectTypeMapper.selectSubjectTypeById(subjectTypeId);
    }

    @Override
    public void updateByIdFilter(SubjectType subjectType) {
        subjectTypeMapper.updateByPrimaryKey(subjectType);
    }


}
