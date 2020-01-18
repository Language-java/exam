package com.alvis.exam.repository;

import com.alvis.exam.domain.Chapter;
import com.alvis.exam.viewmodel.admin.textbook.TextBookPageRequestVM;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper
public interface ChapterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Chapter record);

    int insertSelective(Chapter record);

    int updateByPrimaryKeySelective(Chapter record);

    int updateByPrimaryKey(Chapter record);

    List<Chapter> selectChapterList(TextBookPageRequestVM model);

    void insertChapter(Chapter chapter);

    int updateChapter(Chapter chapter);

    List<Chapter> page(TextBookPageRequestVM model);

    Chapter selectByPrimaryChapterId(String parentId);

    List<Chapter> selectByPrimaryKey(TextBookPageRequestVM model);

    List<Chapter> findChildren(String chapterId);

    List<Chapter> selectListParentId(String chapterId);

    void deleteByIds(List<Object> deleteIds);
}