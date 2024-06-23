package com.yun.demo.annotation;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DistributedLock {
    String key() default "";

    int leaseTime() default 10;

    boolean autoRelease() default true;

    String errorDesc() default "系统正常处理，请稍后提交";

    int waitTime() default 1;
}