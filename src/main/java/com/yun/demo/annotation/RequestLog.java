package com.yun.demo.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** 
 @description: 日志注解
 @author zhangxiaoyun
 @param: @param null 
 @date: 2024/1/1
*/ 
@Retention(RUNTIME) // 必须要是 RUNTIME
@Target(METHOD) // 该注解可以用在方法上 
public @interface RequestLog {
    /**
     * SpEL 表达式
     * @return
     */
    String value();
}