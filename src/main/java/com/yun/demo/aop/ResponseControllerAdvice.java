package com.yun.demo.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yun.demo.annotation.NotResultVoResponse;
import com.yun.demo.vo.ApiException;
import com.yun.demo.vo.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 一个项目下来定义的接口搞个几百个太正常不过了，要是每一个接口返回数据时都要用响应体来包装一下好像有点麻烦，
 * 还是要用到全局处理。
 */
@RestControllerAdvice(basePackages = {"com.yun.*.controller"})
public class ResponseControllerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        // 如果接口返回的类型本身就是ResultVO那就没有必要进行额外的操作，返回false
        return !methodParameter.getParameterType().isAssignableFrom(Result.class)
                || !methodParameter.hasMethodAnnotation(NotResultVoResponse.class );
    }

    @Override
    public Object beforeBodyWrite(Object data, MethodParameter returnType, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest request, ServerHttpResponse response) {
        // String类型不能直接包装，所以要进行些特别的处理
        if (returnType.getGenericParameterType().equals(String.class)) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // 将数据包装在ResultVO里后，再转换为json字符串响应给前端
                return objectMapper.writeValueAsString(Result.success(data));
            } catch (JsonProcessingException e) {
                throw new ApiException("返回String类型错误");
            }
        }
        // 将原本的数据包装在ResultVO里
        return Result.success(data);
    }
}