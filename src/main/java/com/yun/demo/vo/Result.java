package com.yun.demo.vo;

import lombok.Data;

/**
 * @author zxy
 */
@Data
public class Result<T> {

    protected int code;

    protected String message;

    private T data;

    public static <T> Result<T> success(){
        return new Result<>();
    }

    public static <T> Result<T> success(T data){
        return new Result<>(data);
    }

    public static <T> Result<T> fail(String message){
        return new Result<>(StatusCode.FAIL.getCode(),message);
    }

    public Result(){
        this.code = StatusCode.SUCCESS.getCode();
        this.message = StatusCode.SUCCESS.getMsg();
    }

    public Result(StatusCode status){
        this.code = status.getCode();
        this.message = status.getMsg();
    }

    public Result(StatusCode status, T data){
        this.code = status.getCode();
        this.message = status.getMsg();
        this.data = data;
    }

    public Result(int code, String msg){
        this.code = code;
        this.message = msg;
    }

    public Result(T data){
        this.data = data;
        this.code = StatusCode.SUCCESS.getCode();
        this.message = StatusCode.SUCCESS.getMsg();
    }

    public Result(T data, String msg){
        this.data = data;
        this.code = StatusCode.SUCCESS.getCode();
        this.message = msg;
    }

    public Result(int code, T data, String msg){
        this.data = data;
        this.code = code;
        this.message = msg;
    }

    public static <T> Result<T> response(StatusCode statusCode, T msg) {
        return new Result<>(statusCode,msg);
    }
}
