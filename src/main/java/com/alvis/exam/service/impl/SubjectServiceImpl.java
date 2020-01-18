package com.alvis.exam.service.impl;

import com.alvis.exam.domain.Subject;
import com.alvis.exam.repository.SubjectMapper;
import com.alvis.exam.service.SubjectService;
import com.alvis.exam.viewmodel.admin.education.SubjectPageRequestVM;
import com.alvis.exam.viewmodel.admin.textbook.TextBookPageRequestVM;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SubjectServiceImpl extends BaseServiceImpl<Subject> implements SubjectService {

    private final SubjectMapper subjectMapper;
    private final static String CACHE_NAME = "Subject";  //好像没用到,redis中没有该缓存名称

    @Autowired
    public SubjectServiceImpl(SubjectMapper subjectMapper) {
        super(subjectMapper);
        this.subjectMapper = subjectMapper;
    }


    /**
     * 查询
     * 先去缓存CACHE_NAME中去查,查到就不执行里面的方法,返回对象,
     * 查不到该缓存就去调用业务去查询并把数据存到缓存中
     *
     * @param id
     * @return
     */
    @Override
    @Cacheable(value = CACHE_NAME, key = "#id")
    public Subject selectById(Integer id) {
        return super.selectById(id);
    }

    /**
     * 更新 (清除缓存,清除的key,value是哪个缓存的)
     *
     * @param record
     * @return
     */
    @Override
    @CacheEvict(value = CACHE_NAME, key = "#record.id")
    public int updateByIdFilter(Subject record) {
        return super.updateByIdFilter(record);
    }

    @Override
    public List<Subject> getSubjectByLevel(Integer level) {
        return subjectMapper.getSubjectByLevel(level);
    }

    @Override
    public List<Subject> allSubject() {
        return subjectMapper.allSubject();
    }

    @Override
    public Integer levelBySubjectId(Integer id) {
        return this.selectById(id).getLevel();  //通过id找到年级编号
    }

    @Override
    public PageInfo<Subject> page(SubjectPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                subjectMapper.page(requestVM)
        );
    }

}
