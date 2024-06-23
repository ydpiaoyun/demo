/*
package com.yun.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
    */
/**
     * 开启 Redis事务支持
     + @Transactional 注解后，其实是标记了一个Redis 事务块
     * 后续的操作都是在这个事务块中执行的，不会立即返回执行结果，返回 null
     *//*

    @Bean
    public RedisTemplate<String, Object> redisTemplateTransaction(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplateTransaction = new RedisTemplate<>();
        redisTemplateTransaction.setKeySerializer(new StringRedisSerializer());
        redisTemplateTransaction.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplateTransaction.setHashKeySerializer(new StringRedisSerializer());
        redisTemplateTransaction.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        redisTemplateTransaction.setConnectionFactory(connectionFactory);

        redisTemplateTransaction.setEnableTransactionSupport(true);
        return redisTemplateTransaction;
    }
}
*/
