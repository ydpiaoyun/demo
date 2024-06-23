package com.yun.demo.vo;

import lombok.Getter;

/**
 * 错误码code可以使用纯数字,使用不同区间标识一类错误，也可以使用纯字符，也可以使用前缀+编号
 * <p>
 * 错误码：ERR + 编号
 * <p>
 * 可以使用日志级别的前缀作为错误类型区分 Info(I) Error(E) Warning(W)
 * <p>
 * 或者以业务模块 + 错误号
 * <p>
 * TODO 错误码设计
 * <p>
 * Alipay 用了两个code，两个msg(<a href="https://docs.open.alipay.com/api_1/alipay.trade.pay">...</a>)
 * @author zxy
 */

@Getter
public enum StatusCode {
    /**
     * 成功
     */
    SUCCESS(200, "success"),
    /**
     * 失败
     */
    FAIL(500, "failed"),
    /**
     * 未知错误
     */
    UNKNOW_ERROR(510, "未知错误"),
    /**
     * 参数错
     */
    PARAMETER_ERROR(410, "参数错误"),
    /**
     * 认证错误
     */
    TOKEN_EXPIRE(411, "认证过期")
    ;

    private final int code;

    private final String msg;

    StatusCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}