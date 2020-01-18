package com.alvis.exam.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChapterType implements Serializable {
    private static final long serialVersionUID = 5421203035951325337L;

    private Integer typeId;

    private String name;  //版本,册次,章节的名称

    private Integer type; //类型标识
}