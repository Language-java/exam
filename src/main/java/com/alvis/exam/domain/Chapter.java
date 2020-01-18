package com.alvis.exam.domain;

import lombok.Data;

import javax.print.attribute.standard.Destination;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class Chapter implements Serializable {

    private static final long serialVersionUID = 572967080590245060L;

    private Integer id;

    private String subjectTypeId;    //科目id

    private String studyStageId; //学段id

    private String chapterId; //章节列表自动生成唯一uuid

    private String name;  //章节列表名称 (版本,册次,章节,单元)

    private String parentId;  //父id对应 章节列表id

    private Integer status;   //状态,是否启用,数据库还没加字段

    private List<Chapter> Children = new ArrayList<>();//子地区(递归实现)

    private SubjectType subjectType; //学科对象

    private StudyStage studyStage; //学段对象

}