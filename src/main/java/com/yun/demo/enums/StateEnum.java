package com.yun.demo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * @author: zhangxiaoyun
 * @description:
 * @date: 2024-05-20
 */
@Getter
public enum StateEnum {
    NORMAL(1,"正常"),
    FREEZE(2,"冻结");

    @EnumValue // 对应数据库存的值
    @JsonValue // 对应前端展示的值
    private final int value;
    private final String desc;

    StateEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
