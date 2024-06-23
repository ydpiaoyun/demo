package com.yun.demo.state;

/**
* 事件枚举
 * 假设在一个业务系统中，有这样一个对象，它有三个状态：草稿、待发布、发布完成，
 * 针对这三个状态的业务动作也比较简单，分别是：上线、发布、回滚
**/
public enum Events {
    ONLINE,
    PUBLISH,
    ROLLBACK
}