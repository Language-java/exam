package com.alvis.exam.service.impl;

import com.alvis.exam.domain.Knowledge;
import com.alvis.exam.domain.StudyStage;
import com.alvis.exam.domain.SubjectType;
import com.alvis.exam.repository.StudyStageMapper;
import com.alvis.exam.repository.SubjectTypeMapper;
import com.alvis.exam.service.StudyStageService;
import com.alvis.exam.viewmodel.admin.textbook.TextBookPageRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.*;

@Service
public class StudyStageServiceImpl implements StudyStageService {


    private final StudyStageMapper studyStageMapper;

    private final SubjectTypeMapper subjectTypeMapper;

    @Autowired
    public StudyStageServiceImpl(StudyStageMapper studyStageMapper, SubjectTypeMapper subjectTypeMapper) {
        this.studyStageMapper = studyStageMapper;
        this.subjectTypeMapper = subjectTypeMapper;
    }


    @Override
    public List<StudyStage> allStudyStage() {
        return studyStageMapper.selectAllStudyStage();
    }


    @Override
    public void insertStudyStageAndSubject(@Valid TextBookPageRequestVM model) {
        if (model.getStudyStageId() == null || model.getStudyStageId().equals("")) {
            //学段
            StudyStage studyStage = new StudyStage();
            String uuid = UUID.randomUUID().toString().replaceAll("-", ""); //uuid是自己生成,不用数据库加
            studyStage.setStudyStageId(uuid);
            studyStage.setStudyStageName(model.getStudyStageName());
            studyStage.setDeleted(false);
            studyStageMapper.insert(studyStage);
        } else {
            //学科
            SubjectType subjectType = new SubjectType();
            String uuid = UUID.randomUUID().toString().replaceAll("-", ""); //uuid是自己生成,不用数据库加
            subjectType.setSubjectTypeId(uuid); //学科生成的id,不用自增的
            subjectType.setStudyStageId(model.getStudyStageId());
            subjectType.setName(model.getName());
            subjectType.setDeleted(false);
            subjectTypeMapper.insert(subjectType);
        }
    }

    @Override
    public StudyStage getStudyStageById(String studyStageId) {
        return studyStageMapper.selectByPrimaryKey(studyStageId);
    }

    @Override
    public void updateByIdFilter(StudyStage studyStage) {
        studyStageMapper.updateByPrimaryKey(studyStage);
    }

    @Override
    public void updateStudyStage(@Valid TextBookPageRequestVM model) {
        studyStageMapper.updateStudyStage(model);
    }


    @Override
    public Map<String, Object> StudyStage() {
        Map<String, Object> map = new HashMap<>();
        List<StudyStage> StudyStage1 = new ArrayList<>();
        try {
            List<StudyStage> studyStages = studyStageMapper.selectAllStudyStage(); //只查询出学段
            for (StudyStage studyStage : studyStages) {
                if (studyStage != null) {
                    studyStage.setOrderBy("id"); //排序规则
                    List<SubjectType> subjectTypes = subjectTypeMapper.allSubjectType(studyStage.getStudyStageId());//查询学科
                    digui(subjectTypes); //使用递归算法查询学科的,轮番调用
                    studyStage.setSubjectTypes(subjectTypes);
                }
                StudyStage1.add(studyStage);
            }
            map.put("studyStages", StudyStage1); //map是键值对,唯一的key,不能放循环里面,被覆盖
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public void digui(List<SubjectType> subjectTypes) {
        List<StudyStage> retList = new ArrayList<>();
        for (StudyStage s : retList) {
            List<SubjectType> subjectTypes1 = subjectTypeMapper.allSubjectType(s.getStudyStageId());
            if (subjectTypes1.size() > 0) {
                s.setSubjectTypes(subjectTypes1);
                digui(subjectTypes1); //循环调用自己
            }
        }
    }
}
