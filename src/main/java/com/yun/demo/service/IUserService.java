package com.yun.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yun.demo.entity.User;

import java.util.List;

public interface IUserService extends IService<User> {
    User getUserByUserId(Integer userId);

    List<User> queryUsers(String name,Integer minAge,Integer maxAge);

    void updateAgeById(String id,Integer age);

    User getByStatic(Long id);
}