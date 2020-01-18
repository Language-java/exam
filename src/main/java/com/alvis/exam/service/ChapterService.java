package com.alvis.exam.service;


import com.alvis.exam.domain.Chapter;
import com.alvis.exam.viewmodel.admin.textbook.TextBookPageRequestVM;
import com.github.pagehelper.PageInfo;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

public interface ChapterService {

    /**
     * 查询章节列表中的所有内容
     *
     * @param model
     * @return
     */
    List<Chapter> chapterList(TextBookPageRequestVM model);


    /**
     * 使用map集合递归的方法
     *
     * @param model
     * @return
     */
    Map<String, Object> getTree(TextBookPageRequestVM model);


    /**
     * 添加所有类型
     *
     * @param model
     * @return
     */
    Chapter insertChapter(@Valid TextBookPageRequestVM model);

    /**
     * 修改对应类型
     *
     * @param model
     * @return
     */
    Chapter updateChapter(@Valid TextBookPageRequestVM model);

    /**
     * 根据chapterId删除
     *
     * @return
     */

    void deleteByChapterId(String chapterId);

    /**
     * 分页查询
     *
     * @param model
     * @return
     */
    PageInfo<Chapter> page1(TextBookPageRequestVM model);

    /**
     * 分页递归查询
     *
     * @param model
     * @return
     */
    PageInfo<Map<String, Object>> page(TextBookPageRequestVM model);


}
