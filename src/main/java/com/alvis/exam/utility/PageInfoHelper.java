package com.alvis.exam.utility;

import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 把底层分页和其他特性赋给pageInfo对象
 *
 * @author alvis
 */
public class PageInfoHelper {
    //重写分页底层的方法
    public static <T, J> PageInfo<J> copyMap(PageInfo<T> source, Function<? super T, ? extends J> mapper) {
        PageInfo<J> newPage = new PageInfo<>();
        newPage.setPageNum(source.getPageNum());  //每页显示条数
        newPage.setPageSize(source.getPageSize()); //每页记录条数
        newPage.setSize(source.getSize());
        newPage.setStartRow(source.getStartRow());
        newPage.setEndRow(source.getEndRow());
        newPage.setTotal(source.getTotal());
        newPage.setPages(source.getPages());
        newPage.setList(source.getList().stream().map(mapper).collect(Collectors.toList()));
        newPage.setPrePage(source.getPrePage());       //查到的分页数据
        newPage.setNextPage(source.getNextPage());
        newPage.setIsFirstPage(source.isIsFirstPage());
        newPage.setIsLastPage(source.isIsLastPage());
        newPage.setHasPreviousPage(source.isHasPreviousPage());
        newPage.setHasNextPage(source.isHasNextPage());
        newPage.setNavigatePages(source.getNavigatePages());
        newPage.setNavigatepageNums(source.getNavigatepageNums());
        newPage.setNavigateFirstPage(source.getNavigateFirstPage());
        newPage.setNavigateLastPage(source.getNavigateLastPage());
        return newPage;
    }
}
