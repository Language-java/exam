package com.alvis.exam.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Knowledge {
    private Integer id;

    private String knowledgeId;

    private String name;

    private String studyStageId;

    private String subjectTypeId;

    private String parentId;

    private List<Knowledge> children = new ArrayList<>();


}