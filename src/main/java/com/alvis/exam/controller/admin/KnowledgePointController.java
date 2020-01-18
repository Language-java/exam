package com.alvis.exam.controller.admin;

/**
 * Created by webrx on 2019/12/31.
 */

import com.alvis.exam.base.BaseApiController;
import com.alvis.exam.base.RestResponse;
import com.alvis.exam.domain.Chapter;
import com.alvis.exam.domain.Knowledge;
import com.alvis.exam.service.KnowledgeService;
import com.alvis.exam.viewmodel.admin.textbook.TextBookPageRequestVM;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 知识点库信息管理
 */
@Slf4j
@RestController("AdminKnowledgePointController")
@RequestMapping("/api/admin/knowledgePoint")
@AllArgsConstructor
public class KnowledgePointController extends BaseApiController {

    private final KnowledgeService knowledgeService;  //获取分组和知识点

    /**
     * 使用递归查询知识点目录
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public RestResponse<Map<String, Object>> getTreeKnowledgePointList(@RequestBody TextBookPageRequestVM model) {
        model.setOrderBy("id"); //排序规则,可以是倒序,加desc
        Map<String, Object> map = knowledgeService.getTreeKnowledgePointList(model);
        return RestResponse.ok(map);
    }


    /**
     * 添加数据(知识点的)
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public RestResponse<Knowledge> insert(@RequestBody @Valid TextBookPageRequestVM model) {
        Knowledge knowledge = knowledgeService.insertKnowledge(model);
        return RestResponse.ok(knowledge);
    }


    /**
     * 修改,只修改knowledgeId对应的name
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public RestResponse<Knowledge> update(@RequestBody @Valid TextBookPageRequestVM model) {
        Knowledge knowledge = knowledgeService.updateKnowledge(model);
        return RestResponse.ok();
    }


    /**
     * 根据它的唯一id(chapterId)删除
     *
     * @param chapterId
     * @return
     */
    @RequestMapping(value = "/delete/{knowledgeId}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable String knowledgeId) {
        knowledgeService.deleteByKnowledgeId(knowledgeId);
        return RestResponse.ok();
    }
}
