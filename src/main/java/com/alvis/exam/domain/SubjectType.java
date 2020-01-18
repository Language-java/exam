package com.alvis.exam.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubjectType implements Serializable {
    private static final long serialVersionUID = 3283604700492176405L;

    private Integer id;

    private String subjectTypeId; //学科的id ,uuid生成

    private String name; //学科类型名称

    private String studyStageId;  //学段对应的id

    private Boolean deleted; //逻辑删除,修改它


}