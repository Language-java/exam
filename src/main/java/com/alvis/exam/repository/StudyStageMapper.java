package com.alvis.exam.repository;

import com.alvis.exam.domain.StudyStage;
import com.alvis.exam.viewmodel.admin.textbook.TextBookPageRequestVM;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Mapper
public interface StudyStageMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(StudyStage record);

    int insertSelective(StudyStage record);

    StudyStage selectByPrimaryKey(String studyStageId);

    int updateByPrimaryKeySelective(StudyStage record);

    void updateByPrimaryKey(StudyStage record);

    List<StudyStage> selectAllStudyStage();

    void updateStudyStage(@Valid TextBookPageRequestVM model);

}