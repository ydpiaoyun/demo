package com.yun.demo.vo;

import lombok.Getter;
/**
 * @author zxy
 * 只要getter方法，无需setter
 */
@Getter
public class ApiException extends RuntimeException {
    private final int code;
    private final String msg;

    public ApiException() {
        this(1001, "接口错误");
    }

    public ApiException(String msg) {
        this(1001, msg);
    }

    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    public ApiException(StatusCode statusCode) {
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }

}