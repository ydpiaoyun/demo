package com.yun.demo.dynamicdatasource;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 数据源实体，可手动添加数据源
        create table test_db_info(
        id int auto_increment primary key not null comment '主键Id',
        url varchar(255) not null comment '数据库URL',
        username varchar(255) not null comment '用户名',
        password varchar(255) not null comment '密码',
        driver_class_name varchar(255) not null comment '数据库驱动'
        name varchar(255) not null comment '数据库名称'
        )
 **/
@Data
@Accessors(chain = true)
public class DataSourceEntity {

    /**
     * 数据库地址
     */
    private String url;
    /**
     * 数据库用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String passWord;
    /**
     * 数据库驱动
     */
    private String driverClassName;
    /**
     * 数据库key，作为DynamicDataSource中Map中的key
     */
    private String key;
}