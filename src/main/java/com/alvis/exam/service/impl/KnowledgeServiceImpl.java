package com.alvis.exam.service.impl;

import com.alvis.exam.domain.Chapter;
import com.alvis.exam.domain.Knowledge;
import com.alvis.exam.repository.KnowledgeMapper;
import com.alvis.exam.service.KnowledgeService;
import com.alvis.exam.viewmodel.admin.textbook.TextBookPageRequestVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by webrx on 2019/12/31.
 */
@Service
public class KnowledgeServiceImpl implements KnowledgeService {

    private final KnowledgeMapper knowledgeMapper; //知识点的Mapper接口调用

    @Autowired
    public KnowledgeServiceImpl(KnowledgeMapper knowledgeMapper) {
        this.knowledgeMapper = knowledgeMapper;
    }

    @Override
    public Map<String, Object> getTreeKnowledgePointList(TextBookPageRequestVM model) {
        Map<String, Object> map = new HashMap<>();
        List<Knowledge> knowledges1 = new ArrayList<>();
        try {
            List<Knowledge> knowledgeList = knowledgeMapper.selectList(model); //只查询出知识点的父级目录
            for (Knowledge knowledge : knowledgeList) {
                if (knowledge != null) {
                    List<Knowledge> knowledges = knowledgeMapper.findChildren(knowledge.getKnowledgeId());//查询目录下的知识点
                    digui(knowledges); //使用递归算法查询知识点以下的,轮番调用
                    knowledge.setChildren(knowledges);
                }
                knowledges1.add(knowledge);
            }
            map.put("knowledges", knowledges1); //map是键值对,唯一的key,不能放循环里面,被覆盖
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;


    }

    public void digui(List<Knowledge> knowledges) {
        List<Knowledge> retList = new ArrayList<>();
        for (Knowledge c : knowledges) {
            retList = knowledgeMapper.findChildren(c.getKnowledgeId());
            if (retList.size() > 0) {
                c.setChildren(retList);
                digui(retList); //循环调用自己
            }
        }
    }


    @Override
    @Transactional
    public Knowledge insertKnowledge(@Valid TextBookPageRequestVM model) {
        Knowledge knowledge = new Knowledge();
        if (model.getName() != null && !model.getName().equals("")) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", ""); //uuid是自己生成,不用数据库加
            knowledge.setKnowledgeId(uuid);
            knowledge.setName(model.getName());
            knowledge.setStudyStageId(model.getStudyStageId());
            knowledge.setSubjectTypeId(model.getSubjectTypeId());
            if (model.getParentId().length() > 0 && model.getParentId() != null) {
                knowledge.setParentId(model.getParentId());
            }
            knowledgeMapper.insertKnowledge(knowledge);
        }
        return knowledge;
    }

    @Override
    @Transactional
    public Knowledge updateKnowledge(@Valid TextBookPageRequestVM model) {
        Knowledge knowledge = new Knowledge();
        knowledge.setKnowledgeId(model.getKnowledgeId()); //因为接收的是对象,所以它的knowledgeId要设置进来
        knowledge.setName(model.getName());
        knowledgeMapper.updateKnowledge(knowledge);
        return knowledge;
    }


    /**
     * 批量删除
     *
     * @param knowledgeId
     */
    @Override
    public void deleteByKnowledgeId(String knowledgeId) {
        ArrayList<Object> deleteIds = new ArrayList<>();
        deleteIds.add(knowledgeId);
        findSubNode(deleteIds, knowledgeId);
        knowledgeMapper.deleteByIds(deleteIds);
    }

    private void findSubNode(ArrayList<Object> deleteIds, String knowledgeId) {
        Knowledge knowledge = new Knowledge();
        knowledge.setParentId(knowledgeId);
        List<Knowledge> knowledges = knowledgeMapper.findChildren(knowledgeId);
        for (Knowledge knowledge1 : knowledges) {
            deleteIds.add(knowledge1.getKnowledgeId());
            findSubNode(deleteIds, knowledge1.getKnowledgeId());
        }
    }
}
