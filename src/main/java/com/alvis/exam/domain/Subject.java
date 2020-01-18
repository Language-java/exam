package com.alvis.exam.domain;


import java.io.Serializable;

public class Subject implements Serializable {

    private static final long serialVersionUID = 8058095034457106501L;

    private Integer id;

    private String name;  //学科

    private Integer level;    //年级编号

    private String levelName; //年级

    private Integer studyStageId; //学段id

    private Integer itemOrder;

    public Integer getStudyStageId() {
        return studyStageId;
    }

    public void setStudyStageId(Integer studyStageId) {
        this.studyStageId = studyStageId;
    }


    public Integer getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }
}
