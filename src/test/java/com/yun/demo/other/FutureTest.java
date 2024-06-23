package com.yun.demo.other;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author: zhangxiaoyun
 * @description:
 * @date: 2023/9/14
 */
@Slf4j
public class FutureTest {

    private static Random random = new Random();

    @Test
    public void streamFlatMapTest() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            randomSleep();
            System.out.println("hello");
            return "hello";});
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            randomSleep();
            System.out.println("world");
            return "world";
        });
        CompletableFuture<Object> result = CompletableFuture.anyOf(future1, future2);
        System.out.println(result.get());
    }

    @Test
    public void streamSumTest() {
    }

    private static void randomSleep() {

        try {
            Thread.sleep(random.nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
