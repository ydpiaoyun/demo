package com.yun.demo.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
    @ExcelProperty("用户名")
    private String username;

    @ExcelProperty("手机号")
    private String mobile;

    @ExcelProperty("邮箱")
    private String email;

    @ExcelProperty("创建时间")
    private Date creteTime;

    @ExcelProperty("工资")
    private double salary;

    @ExcelIgnore
    private String password;

    @ExcelIgnore
    private String salt;

    public UserInfo() {
    }

    public UserInfo(String username, String password, String salt) {
        this.username = username;
        this.password = password;
        this.salt = salt;
    }
}