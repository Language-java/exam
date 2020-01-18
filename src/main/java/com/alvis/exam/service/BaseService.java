package com.alvis.exam.service;

/**
 * 缓存接口,两个实现公用一个
 *
 * @param <T>
 */
public interface BaseService<T> {
    int deleteById(Integer id);

    int insert(T record);

    int insertByFilter(T record);

    T selectById(Integer id);

    int updateByIdFilter(T record);

    int updateById(T record);

}
