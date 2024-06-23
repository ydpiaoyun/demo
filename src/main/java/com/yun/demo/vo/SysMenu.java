package com.yun.demo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: zhangxiaoyun
 * @description:
 * @date: 2023/8/23
 */
@Data
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String permissions;
    private String url;
    private String description;
    private String iconCls;
    private Integer pid;
    private Integer status;
    private Integer resourceType;
    private Integer sort;
    private Date createTime;
    private Date updateTime;

    private List<SysMenu> children;

}