package com.alvis.exam.service;

/**
 * Created by webrx on 2019/12/27.
 */

import com.alvis.exam.domain.ChapterType;

import java.util.List;

/**
 * 查询类型
 */
public interface ChapterTypeService {
    /**
     * 查询类型(版本册次章节)
     *
     * @return
     */
    List<ChapterType> chapterTypeList();
}
