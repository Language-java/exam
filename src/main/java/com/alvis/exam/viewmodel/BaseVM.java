package com.alvis.exam.viewmodel;

import com.alvis.exam.utility.ModelMapperSingle;
import lombok.Data;
import org.modelmapper.ModelMapper;

/**
 * @author alvis  modelMapper作用可以把敏感词汇设置隐藏起来(密码、加密token等)
 *         直接new出一个ModelMapper对象，并且调用其map方法将指定对象的值映射到另一个对象上了
 */

@Data
public class BaseVM {
    protected static ModelMapper modelMapper = ModelMapperSingle.Instance();


}
