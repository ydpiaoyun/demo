package com.yun.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yun.demo.config.MyBaseMapper;
import com.yun.demo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserMapper extends MyBaseMapper<User> {
    void updateAgeByIds(@Param(Constants.WRAPPER) LambdaQueryWrapper<User> wrapper, @Param("age") int age);

    @Update("update sys_user set age = age + #{age} where id = #{id}")
    void addAgeById(@Param("id") Long id, @Param("age") int age);
}