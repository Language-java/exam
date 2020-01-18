package com.alvis.exam.repository;

import com.alvis.exam.domain.ChapterType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChapterTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChapterType record);

    int insertSelective(ChapterType record);

    ChapterType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChapterType record);

    int updateByPrimaryKey(ChapterType record);

    List<ChapterType> selectChapterTypeList();

}