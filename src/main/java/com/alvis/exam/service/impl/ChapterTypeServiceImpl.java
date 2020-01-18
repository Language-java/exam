package com.alvis.exam.service.impl;

import com.alvis.exam.domain.ChapterType;
import com.alvis.exam.repository.ChapterTypeMapper;
import com.alvis.exam.service.ChapterTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by webrx on 2019/12/27.
 */

@Service
public class ChapterTypeServiceImpl implements ChapterTypeService {


    private final ChapterTypeMapper chapterTypeMapper;

    @Autowired
    public ChapterTypeServiceImpl(ChapterTypeMapper chapterTypeMapper) {
        this.chapterTypeMapper = chapterTypeMapper;
    }


    @Override
    public List<ChapterType> chapterTypeList() {
        return chapterTypeMapper.selectChapterTypeList();
    }
}
