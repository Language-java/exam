package com.alvis.exam.service.impl;


import com.alvis.exam.domain.Chapter;
import com.alvis.exam.repository.ChapterMapper;
import com.alvis.exam.service.ChapterService;
import com.alvis.exam.viewmodel.admin.textbook.TextBookPageRequestVM;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.*;

@Service
public class ChapterServiceImpl implements ChapterService {

    private final ChapterMapper chapterMapper;

    @Autowired
    public ChapterServiceImpl(ChapterMapper chapterMapper) {
        this.chapterMapper = chapterMapper;
    }


    @Override
    public List<Chapter> chapterList(TextBookPageRequestVM model) {
        model.setOrderBy("id"); //排序规则 ,通过id排序
        List<Chapter> chapterList = chapterMapper.selectChapterList(model);
        return chapterList;
    }


    //可以实现(递归方式)
    @Override
    public Map<String, Object> getTree(TextBookPageRequestVM model) {
        Map<String, Object> map = new HashMap<>();
        List<Chapter> chapters1 = new ArrayList<>();
        try {
            List<Chapter> chapterList = chapterMapper.selectByPrimaryKey(model); //查询出所有版本
            for (Chapter chapter : chapterList) {
                if (chapter != null) {
                    List<Chapter> chapters = chapterMapper.findChildren(chapter.getChapterId());//查询版本下面的
                    digui(chapters); //调用递归算法查询册次以下的
                    chapter.setChildren(chapters);
                }
                chapters1.add(chapter);
            }
            map.put("chapters", chapters1); //map是键值对,唯一的key,不能放循环里面,被覆盖
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 递归循环代码
     *
     * @param chapters
     */
    public void digui(List<Chapter> chapters) {
        List<Chapter> retList = new ArrayList<>();
        for (Chapter c : chapters) {
            retList = chapterMapper.findChildren(c.getChapterId());
            if (retList.size() > 0) {
                c.setChildren(retList);
                digui(retList); //循环调用自己
            }
        }
    }


    @Override
    @Transactional
    public Chapter insertChapter(TextBookPageRequestVM model) {
        Chapter chapter = new Chapter();
        if (model.getName() != null && !model.getName().equals("")) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", ""); //uuid是自己生成,不用数据库加
            chapter.setChapterId(uuid);
            chapter.setName(model.getName());
            chapter.setStudyStageId(model.getStudyStageId());
            chapter.setSubjectTypeId(model.getSubjectTypeId());
            if (model.getParentId().length() > 0) {   //意思就是有parentId设置,没有就不设置,(版本parentId)
                chapter.setParentId(model.getParentId());
            }
            chapterMapper.insertChapter(chapter);
        }
        return chapter;
    }


    @Override
    @Transactional
    public Chapter updateChapter(@Valid TextBookPageRequestVM model) {
        Chapter chapter = new Chapter();
        chapter.setChapterId(model.getChapterId()); //因为接收的是对象,所以它的ChapterId要设置进来
        chapter.setName(model.getName());
        chapterMapper.updateChapter(chapter);
        return chapter;
    }


    /**
     * 批量删除操作(递归)
     *
     * @param chapterId
     */
    @Override
    public void deleteByChapterId(String chapterId) {
        List<Object> deleteIds = new ArrayList<Object>(); //把子节点的id设置成一个集合
        deleteIds.add(chapterId); //设置节点的id到集合中
        //递归查询所有子类目
        findSubNode(deleteIds, chapterId);
        //执行批量删除
        this.deleteByIds(deleteIds);
    }

    private void deleteByIds(List<Object> deleteIds) {
        chapterMapper.deleteByIds(deleteIds); //批量删除的mapper
    }

    private void findSubNode(List<Object> deleteIds, String chapterId) {
        Chapter chapter = new Chapter();
        chapter.setParentId(chapterId);
        //查询当前分类的子类目
        List<Chapter> list = this.chapterMapper.findChildren(chapterId); //查询节点信息
        for (Chapter chapter1 : list) {
            //把子类目的id放入集合中
            deleteIds.add(chapter1.getChapterId());
            findSubNode(deleteIds, chapter1.getChapterId());
        }
    }


    @Override
    public PageInfo<Chapter> page1(TextBookPageRequestVM model) {
        return PageHelper.startPage(model.getPageIndex(), model.getPageSize(), "subject_type_id").doSelectPageInfo(() ->
                new PageInfo<>(chapterMapper.page(model))//把集合设置到pageInfo中
        );
    }


    @Override
    public PageInfo<Map<String, Object>> page(TextBookPageRequestVM model) {
        PageHelper.startPage(model.getPageIndex(), model.getPageSize());
        List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
        try {
            List<Chapter> chapterList = chapterMapper.selectByPrimaryKey(model); //查询出所有版本
            for (Chapter chapter : chapterList) {
                if (chapter != null) {
                    List<Chapter> chapters = chapterMapper.findChildren(chapter.getChapterId());//查询版本下面的
                    digui(chapters); //调用递归算法查询册次以下的
                    chapter.setChildren(chapters);
                }
                HashMap<String, Object> map = new HashMap<>();
                map.put("page", chapter);
                list1.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new PageInfo<Map<String, Object>>(list1);
    }
}
