package com.alvis.exam.repository;

import com.alvis.exam.domain.Knowledge;
import com.alvis.exam.viewmodel.admin.textbook.TextBookPageRequestVM;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface KnowledgeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Knowledge record);

    int insertSelective(Knowledge record);

    Knowledge selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Knowledge record);

    int updateByPrimaryKey(Knowledge record);


    List<Knowledge> findChildren(String knowledgeId);

    List<Knowledge> selectList(TextBookPageRequestVM model);

    void insertKnowledge(Knowledge knowledge);

    void updateKnowledge(Knowledge knowledge);

    void deleteByIds(ArrayList<Object> deleteIds);
}