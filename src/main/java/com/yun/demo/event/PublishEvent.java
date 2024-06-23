package com.yun.demo.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author: zhangxiaoyun
 * @description:
 * @date: 2023/12/14
 */
@SpringBootApplication
@EnableAsync
public class PublishEvent {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(PublishEvent.class, args);
        ctx.publishEvent(new MyEvent(new PublishEvent(), "javaboy"));
    }
}
