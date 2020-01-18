package com.alvis.exam.service;


import com.alvis.exam.domain.Knowledge;
import com.alvis.exam.viewmodel.admin.textbook.TextBookPageRequestVM;

import javax.validation.Valid;
import java.util.Map;

/**
 * Created by webrx on 2019/12/31.
 */


public interface KnowledgeService {

    /**
     * 使用递归查询知识点目录
     *
     * @param model
     * @return
     */
    Map<String, Object> getTreeKnowledgePointList(TextBookPageRequestVM model);


    /**
     * 添加知识点列表数据
     *
     * @param model
     * @return
     */
    Knowledge insertKnowledge(@Valid TextBookPageRequestVM model);


    /**
     * 修改知识点数据
     *
     * @param model
     * @return
     */
    Knowledge updateKnowledge(@Valid TextBookPageRequestVM model);

    /**
     * 根据对应的知识点knowledgeId 进行递归删除
     *
     * @param knowledgeId
     */
    void deleteByKnowledgeId(String knowledgeId);
}
