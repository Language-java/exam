package com.alvis.exam.repository;

import com.alvis.exam.domain.SubjectType;
import com.alvis.exam.viewmodel.admin.textbook.TextBookPageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import javax.validation.Valid;
import java.util.List;

@Mapper
public interface SubjectTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SubjectType record);

    int insertSelective(SubjectType record);

    SubjectType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SubjectType record);

    void updateByPrimaryKey(SubjectType record);

    List<SubjectType> allSubjectStudyStageId(TextBookPageRequestVM model);

    void updateSubjectType(@Valid TextBookPageRequestVM model);


    SubjectType selectSubjectTypeById(String subjectTypeId);


    List<SubjectType> allSubjectType(String studyStageId);
}