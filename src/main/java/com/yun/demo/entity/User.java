package com.yun.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.yun.demo.annotation.Desensitization;
import com.yun.demo.enums.DesensitizationTypeEnum;
import com.yun.demo.enums.StateEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;

/**
 * <p>
 * 用户实体对应表 user
 * </p>
 *
 * @author hubin
 * @since 2018-08-11
 */
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class User {

    /**
     * 默认类型为 ASSIGN_ID，这是雪花算法
     * mybatisplus 默认将 “id”识别为主键列，如果属性名字不是这个需要添加 @TableId 注解标识为主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(condition = SqlCondition.LIKE, jdbcType = JdbcType.VARCHAR)
    private String name;
    private Integer age;

    @Desensitization(type = DesensitizationTypeEnum.MOBILE_PHONE)
    private String phone;

    @Desensitization(type = DesensitizationTypeEnum.PASSWORD)
    private String password;

    @Desensitization(type = DesensitizationTypeEnum.MY_RULE, startInclude = 0, endExclude = 2)
    private String address;

    private String email;
    @TableField(exist = false)
    private String ignoreColumn = "ignoreColumn";

    @TableField(exist = false)
    private Integer count;

    private StateEnum status;

    @Version
    private Integer version;

    /**
     * 数据库中 deleted，会作为where条件出现在自动生成的sql中
     * 数据库中列名是带 is 的，阿里规范建议 java属性中不带。 1 表示删除， 0 表示未删除
     */
    @TableLogic
    @TableField("deleted")
    private Boolean deleted;
}