/*
package com.yun.demo.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.redisson.api.*;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@Slf4j
class RedissonTests {

    @Autowired
    private RedissonClient redissonClient;

    private int count;

    @Test
    public void strDataTest() {
        RBucket<String> rbucket =  redissonClient.getBucket("test");
        rbucket.set("bug",3, TimeUnit.SECONDS);
        String test = rbucket.get();
        log.info(test);
    }

    @Test
    public void listTest() {
        RList<String> rList = redissonClient.getList("list");
        rList.add("孔子");
        rList.add("孟子");
        rList.add("荀子");
        final List<String> result = rList.readAll();
        log.info(rList.toString());
    }

    @Test
    public void setTest() {
        RSet<String> rSet = redissonClient.getSet("set", StringCodec.INSTANCE);
        rSet.add("孔子-性善论");
        rSet.add("孟子-老师（孔子孙子子思）");
        rSet.add("荀子-性恶论");
        rSet.add("韩非");
        rSet.add("秦始皇宰相李斯");
        rSet.add("lihm");
        rSet.forEach(log::info);
    }

    @Test
    public void sortedSetTest() {
        RScoredSortedSet<String> rSet = redissonClient.getScoredSortedSet("ScoredSortedSet");
        rSet.add(0.33, "孔子-性善论");
        rSet.add(0.251, "孟子-老师（孔子孙子子思）");
        rSet.add(0.302, "荀子-性恶论");
        rSet.add(0.5, "韩非");
        rSet.add(1.0, "秦始皇宰相李斯");
        rSet.add(1.2, "lihm");
//        rSet.rank("lihm"); // 获取元素在集合中的位置
//        rSet.getScore("lihm"); // 获取元素的评分
        rSet.forEach(log::info);
    }

    @Test
    public void hashTest() {
        RMap<String, String> map = redissonClient.getMap("hash");
        map.put("name", "lihm");
        map.put("email", "sunshine1028@foxmail.com");
        map.forEach((key, value) -> log.info(key + ":" + value));
    }

    @Test
    public void bloomFilterTest() {
        RBloomFilter<String> seqIdBloomFilter = redissonClient.getBloomFilter("seqId");
        // 初始化预期插入的数据量为10000000和期望误差率为0.01
        seqIdBloomFilter.tryInit(10000000, 0.01);
        // 插入部分数据
        seqIdBloomFilter.add("123");
        seqIdBloomFilter.add("456");
        seqIdBloomFilter.add("789");
        // 判断是否存在
        System.out.println(seqIdBloomFilter.contains("123"));
        System.out.println(seqIdBloomFilter.contains("789"));
        System.out.println(seqIdBloomFilter.contains("100"));
    }

    @Test
    public void lockTest() throws InterruptedException {
        String LOCKKEY = "rediss:lock";
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                // 每个线程都创建自己的锁对象
                @Override
                public void run() {
                    RLock lock = redissonClient.getLock(LOCKKEY);
                    try {
                        lock.lock();
                        log.info(Thread.currentThread().getName()+" locked");
                        count = count + 1;
                    } finally{
                        lock.unlock();
                    }
                    countDownLatch.countDown();
                }
            }).start();
        }

        countDownLatch.await();
        log.info("count = {}", this.count);
    }

    @Test
    public void queueTest() {
        RQueue<String> queue = redissonClient.getQueue("anyQueue");
        queue.add("abc");
        String someObj = queue.poll();
        log.info(someObj);

        RPriorityQueue<Integer> priorityQueue = redissonClient.getPriorityQueue("anyQueue");
        priorityQueue.add(3);
        priorityQueue.add(1);
        priorityQueue.add(2);

        priorityQueue.removeAsync(0);
        priorityQueue.addAsync(5);

        log.info("first element is: "+priorityQueue.poll());
    }

    @Test
    public void rateLimiterTest() throws Exception {
        RRateLimiter rateLimiter = redissonClient.getRateLimiter("rate_limiter");
        */
/**
         * 1秒之内允许访问数
         * 注意trySetRate和setRate 区别
         * trySetRate 场所设置如果已存在不修改，返回false，否者为true
         * setRate 更新配置
         *//*

        rateLimiter.trySetRate(RateType.OVERALL, 4, 1, RateIntervalUnit.SECONDS);
        rateLimiter.setRate(RateType.OVERALL, 4, 1, RateIntervalUnit.SECONDS);
        log.info("config: " + new ObjectMapper().writeValueAsString(rateLimiter.getConfig()));

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                log.info("线程" + Thread.currentThread().getId() + " availablePermits：" + rateLimiter.availablePermits());
                rateLimiter.acquire(2);// 每次请求占用2个资源数
                log.info("线程" + Thread.currentThread().getId() + "进入数据区：" + System.currentTimeMillis());
            });
        }
        TimeUnit.SECONDS.sleep(5);
    }



}
*/
