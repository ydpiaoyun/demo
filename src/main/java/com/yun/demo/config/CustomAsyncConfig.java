package com.yun.demo.config;

import com.yun.demo.component.GlobalAsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 默认情况下，Spring使用SimpleAsyncTaskExecutor异步运行这些方法
 * 可以在两个级别上重写默认线程池——应用程序级别或方法级别。
 * 1、方法级别：通过@Bean声明 Executor,然后，在@Async中的属性提供Executor名称  @Async("threadPoolTaskExecutor")
 * 2、应用级别：配置类应实现AsyncConfigurer接口，重写getAsyncExecutor()方法。将返回整个应用程序的Executor，
 *      这样一来，它就成为运行以@Async注释的方法的默认Executor：
 * @author zxy
 */
@Configuration
@EnableAsync
public class CustomAsyncConfig implements AsyncConfigurer {

    /**
     * 对线程池进行配置
     * @return
     */
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // corePoolSize：线程池维护线程的最少数量
        taskExecutor.setCorePoolSize(20);
        // maxPoolSize：线程池维护线程的最大数量,只有在缓冲队列满了之后才会申请超过核心线程数的线程
        taskExecutor.setMaxPoolSize(200);
        // queueCapacity：缓存队列容量
        taskExecutor.setQueueCapacity(25);
        // keepAliveSeconds：允许的空闲时间,当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        taskExecutor.setKeepAliveSeconds(200);
        taskExecutor.setThreadNamePrefix("oKong-");
        // 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
        // CallerRunsPolicy策略，当线程池没有处理能力的时候，直接在 execute 方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务。
        // AbortPolicy策略：处理程序遭到拒绝将抛出运行时RejectedExecutionException。
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.initialize();
        return taskExecutor;
    }

    /**
     * 返回自定义异常处理
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new GlobalAsyncExceptionHandler();
    }
}