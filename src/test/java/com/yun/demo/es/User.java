package com.yun.demo.es;

import lombok.Data;

/**
 * @author: zhangxiaoyun
 * @description:
 * @date: 2023/12/29
 */
@Data
public class User {

    private String id;
    private String name;
    private Integer age;
    private String[] habbys;

    public User() {}
    public User(String name, int age, String[] habbys) {
        this.name = name;
        this.age = age;
        this.habbys = habbys;
    }
}
