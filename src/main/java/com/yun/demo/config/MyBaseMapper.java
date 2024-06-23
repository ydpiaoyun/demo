package com.yun.demo.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyBaseMapper<T> extends BaseMapper<T> {

    // 批量插入,方法名必须为 insertBatchSomeColumn, 和 InsertBatchSomeColumn 内部定义好的方法名保持一致。
    int insertBatchSomeColumn(@Param("list") List<T> batchList);

}