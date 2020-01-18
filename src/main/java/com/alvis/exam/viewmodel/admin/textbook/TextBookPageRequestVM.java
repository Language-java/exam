package com.alvis.exam.viewmodel.admin.textbook;

import com.alvis.exam.base.BasePage;
import com.alvis.exam.domain.Chapter;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求数据
 */
@Data
public class TextBookPageRequestVM extends BasePage {

    private Integer id; //id

    private String subjectTypeId;//科目id

    private String studyStageId; //学段id

    private String chapterId; //章节列表自动生成唯一uuid

    private String name;  //章节列表名称 (版本,册次,章节,单元)

    private String parentId;  //父id对应 章节列表id

    private String knowledgeId; //知识点的列表id自动生成uuid

    private String orderBy;  //排序规则

    private String studyStageName; //学段名称


}
