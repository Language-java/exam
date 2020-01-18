package com.alvis.exam.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 学段
 */
@Data
public class StudyStage implements Serializable {

    private static final long serialVersionUID = -599453347204090486L;
    private Integer id;
    private String studyStageId;  //学段uuid
    private String studyStageName;  //学段名称
    private Boolean deleted; //逻辑删除,修改它

    private String orderBy;  //排序规则

    private List<SubjectType> subjectTypes = new ArrayList<>();//可以把学科作为学段的儿子

}