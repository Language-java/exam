package com.alvis.exam.repository;

import com.alvis.exam.domain.Test;

public interface TestMapper {
    int deleteByPrimaryKey(String testId);

    int insert(Test record);

    int insertSelective(Test record);

    Test selectByPrimaryKey(String testId);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKeyWithBLOBs(Test record);

    int updateByPrimaryKey(Test record);
}