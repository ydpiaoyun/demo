package com.yun.demo.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.demo.entity.User;
import com.yun.demo.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * @author: zhangxiaoyun
 * @description:
 * @date: 2024-05-20
 */
@SpringBootTest
public class UserTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private UserMapper userMapper;

    @Test
    void testAddUser() {
        User user = new User();
        user.setId(10L).setName("张三")
                .setAge(18)
                .setAddress("北京市")
                .setEmail("");
        userService.save(user);
    }

    @Test
    void testQueryUser() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id","age")
                .like("name","A")
                .ge("age",10);
        userService.list(wrapper).forEach(System.out::println);
    }

    @Test
    void testLambdaQueryUser() {
        String email = "admin";
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getAge,User::getAge)
                .like(User::getName,"A")
                .like(StringUtils.isNotBlank(email),User::getEmail,email)
                .ge(User::getAge,10);
        userService.list(wrapper).forEach(System.out::println);
    }

    @Test
    void testUpdateByQueryWrapper() {
        User user = new User();
        user.setAge(20);
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("name", "AJone");
        userService.update(user, wrapper);
    }

    // 更新id为 1，2 的记录，年龄加 1
    @Test
    void testUpdateByUpdateWrapper() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>().setSql("age = age-1")
                .in("id", Arrays.asList(1L, 2L));
        userService.update(null, updateWrapper);
    }

    @Test
    void testUpdateCustom() {
        int age = 20;
        LambdaQueryWrapper<User> wrapper = new QueryWrapper<User>().lambda()
                .in(User::getId, Arrays.asList(1L, 2L));
        userMapper.updateAgeByIds(wrapper,age);
    }

    @Test
    void testPage() {
        String name = "",sortBy="";
        int pageNo = 2,pageSize = 3;
        Page<User> page = Page.of(pageNo, pageSize);
        if(StrUtil.isBlank(sortBy)){
            page.addOrder(new OrderItem("id",false));
        }else {
            page.addOrder(new OrderItem("age",false));
        }
        Page<User> pageData = userService.lambdaQuery()
                .like(StrUtil.isNotBlank(name), User::getName, name)
                .page(page);
        System.out.println("总条数:"+pageData.getTotal());
        System.out.println("总页数:"+pageData.getPages());
        System.out.println("本页条数:"+pageData.getRecords().size());
        pageData.getRecords().forEach(System.out::println);
    }
}