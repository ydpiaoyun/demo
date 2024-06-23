package com.yun.demo.service.other;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.concurrent.TimeoutException;

@Service
public class ThirdPartyService {

    /**
     @description: @Retryable注解标记了callThirdPartyApi方法，
     指定了当发生RestClientException异常时进行重试。maxAttempts指定最大重试次数，
     backoff指定了重试间隔的初始延迟和延迟倍数
    */
    @Retryable(
        value = { RestClientException.class, TimeoutException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public String callThirdPartyApi() {
        // 调用第三方API的逻辑
        return null;
    }

    /**
     @description: @Recover注解标记了fallback方法，
     当callThirdPartyApi方法的重试次数达到上限时，将执行fallback方法中的降级逻辑
    */
    @Recover
    public String fallback() {
        // 降级处理逻辑
        return null;
    }

}