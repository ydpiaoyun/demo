package com.yun.demo.util;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: zhangxiaoyun
 * @description:
 * @date: 2023/9/23
 */
@Mapper
public interface BeanMapper {
    BeanMapper INSTANCE = Mappers.getMapper(BeanMapper.class);
}
