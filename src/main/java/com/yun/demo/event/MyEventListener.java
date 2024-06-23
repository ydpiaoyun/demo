package com.yun.demo.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/** 
 @description: 事件监听器有两种定义方式，第一种是自定义类实现 ApplicationListener 接口
*/
@Component
public class MyEventListener implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent event) {
        System.out.println("event = " + event);
    }
}