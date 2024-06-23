package com.yun.demo.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 @description: 事件监听器有两种定义方式，第二种方式则是通过注解去标记事件消费方法
*/
@Component
public class MyEventListener02 {
    @EventListener(value = MyEvent.class)
    public void hello(MyEvent event) {
        System.out.println("event02 = " + event);
    }
}