package com.yun.demo.component;


import com.yun.demo.vo.ApiException;
import com.yun.demo.vo.Result;
import com.yun.demo.vo.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zxy
 */
@Slf4j
@RestControllerAdvice("com.yun.demo.controller")
public class GlobalExceptionHandler {
    /**
     * 统一处理请求参数缺失异常
     * 出现404的时候，默认是不抛NoHandlerFoundException异常，而是forward跳转到/error控制器
     *  需要增加配置
     *      spring.mvc.throw-exception-if-no-handler-found=true
     *      spring.resources.add-mappings=false
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            NoHandlerFoundException.class,
            ServletRequestBindingException.class,
            MissingServletRequestPartException.class,
            AsyncRequestTimeoutException.class
    })

    public Result<String> servletRequestException(MissingServletRequestParameterException e){
        log.info("请求异常：{}", e.getMessage());
        String missingParameterName = e.getParameterName();
        return Result.response(StatusCode.PARAMETER_ERROR,"缺少参数{" + missingParameterName + "}");
    }

    /**
     * 使用form data方式调用接口，校验异常抛出 BindException
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
            BindException.class,
            ConversionNotSupportedException.class})
    public Result<String> bindException(BindException e) {
        log.info("参数绑定异常：{}", e.getMessage());
        return Result.response(StatusCode.PARAMETER_ERROR,wrapperBindingResult(e.getBindingResult()));
    }

    /**
     * 单个参数校验异常抛出 ConstraintViolationException
     */
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> constraintViolationException(ConstraintViolationException ex) {
        log.info("普通参数异常：{}", ex.getMessage());

        StringBuilder msgBuilder = new StringBuilder();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = path.toString().split("\\.");
            msgBuilder.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        msgBuilder = new StringBuilder(msgBuilder.substring(0, msgBuilder.length() - 1));
        return Result.response(StatusCode.PARAMETER_ERROR,msgBuilder.toString());
    }

    /**
     * 使用 json 请求体调用接口，校验异常抛出 MethodArgumentNotValidException
     * 统一处理请求参数校验(被@RequestBody注解的实体对象传参)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("参数校验异常：{}", e.getMessage());
        return Result.response(StatusCode.PARAMETER_ERROR,wrapperBindingResult(e.getBindingResult()));
    }

    /**
     * 参数格式错误
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("错误信息{}", e.getLocalizedMessage());
        return Result.response(StatusCode.PARAMETER_ERROR,"参数格式错误");
    }

    /**
     * 参数格式错误
     */
    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            IllegalArgumentException.class,
            HttpMessageNotWritableException.class,
            HttpRequestMethodNotSupportedException.class,
            MissingPathVariableException.class,
            HttpMediaTypeNotAcceptableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<String> httpRequestException(HttpMessageNotReadableException e) {
        log.error("错误信息:{}", e.getLocalizedMessage());
        return Result.response(StatusCode.PARAMETER_ERROR,"参数格式或请求方式错误");
    }

    /**
     * SQL异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLException.class)
    public Result<String> sqlException(SQLException e) {
        log.info("SQL异常:{}", e.getMessage());
        return Result.response(StatusCode.UNKNOW_ERROR,e.getMessage());
    }

    /**
     * 自定义Api异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApiException.class)
    public Result<String> apiException(Exception e) {
        log.info("系统内部异常:{}", e.getMessage());
        return Result.response(StatusCode.UNKNOW_ERROR,e.getMessage());
    }

    /**
     * 通用异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result<String> exception(Exception e) {
        log.info("系统内部异常:{}", e.getMessage());
        return Result.response(StatusCode.UNKNOW_ERROR,e.getMessage());
    }

    /**
     * 所有异常的拦截
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<String> throwable(Throwable e) {
        log.error("系统内部异常：", e);
        return Result.response(StatusCode.UNKNOW_ERROR,e.getMessage());
    }

    private String wrapperBindingResult(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        return fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
    }
}