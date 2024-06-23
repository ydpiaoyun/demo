package com.yun.demo.event;

import org.springframework.context.ApplicationEvent;

public class MyEvent extends ApplicationEvent {

    // 自定义事件的属性，根据需要定义
    private String name;
    public MyEvent(Object source, String name) {
        super(source);
        this.name = name;
    }

    @Override
    public String toString() {
        return "MyEvent{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}