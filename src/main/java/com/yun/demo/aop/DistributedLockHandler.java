/*
package com.yun.demo.aop;

import com.yun.demo.annotation.DistributedLock;
import com.yun.demo.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Aspect
//@Component
@Slf4j
public class DistributedLockHandler {

    @Autowired
    private Redisson redisson;

    */
/**
     * 加锁操作(tryLock锁，没有等待时间）
     * @param lockName  锁名称
     * @param leaseTime 锁有效时间
     *//*

    public boolean tryLock(String lockName, long leaseTime) {

        RLock rLock = redisson.getLock(lockName);
        boolean getLock = false;
        try {
            getLock = rLock.tryLock( leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("获取Redisson分布式锁[异常]，lockName=" + lockName, e);
            e.printStackTrace();
            return false;
        }
        return getLock;
    }

    */
/**
     * 加锁操作(tryLock锁，有等待时间）
     * @param lockName   锁名称
     * @param leaseTime  锁有效时间
     * @param waitTime   等待时间
     *//*

    public  boolean tryLock(String lockName, long leaseTime,long waitTime) {

        RLock rLock = redisson.getLock(lockName);
        boolean getLock = false;
        try {
            getLock = rLock.tryLock( waitTime,leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("获取Redisson分布式锁[异常]，lockName=" + lockName, e);
            e.printStackTrace();
            return false;
        }
        return getLock;
    }

    */
/**
     * 解锁
     * @param lockName  锁名称
     *//*

    public void unlock(String lockName) {
        redisson.getLock(lockName).unlock();
    }

    */
/**
     * 判断该锁是否已经被线程持有
     * @param lockName  锁名称
     *//*

    public boolean isLock(String lockName) {
        RLock rLock = redisson.getLock(lockName);
        return rLock.isLocked();
    }


    */
/**
     * 判断该线程是否持有当前锁
     * @param lockName  锁名称
     *//*

    public boolean isHeldByCurrentThread(String lockName) {
        RLock rLock = redisson.getLock(lockName);
        return rLock.isHeldByCurrentThread();
    }

    @Around("@annotation(distributedLock)")
    public Object around(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
        String lockName = this.getRedisKey(joinPoint, distributedLock);
        int leaseTime = distributedLock.leaseTime();
        String errorDesc = distributedLock.errorDesc();
        int waitTime = distributedLock.waitTime();

        Object var8;
        try {
            boolean lock = tryLock(lockName, (long)leaseTime, (long)waitTime);
            if (!lock) {
                throw new RuntimeException(errorDesc);
            }

            var8 = joinPoint.proceed();
        } catch (Throwable var12) {
            log.error("执行业务方法异常", var12);
            throw var12;
        } finally {
            if (isHeldByCurrentThread(lockName)) {
                unlock(lockName);
            }

        }

        return var8;
    }


    */
/**
     *  获取加锁的key
     * @param joinPoint
     * @param distributedLock
     * @return
     *//*

    private String getRedisKey(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) {
        String key = distributedLock.key();
        Object[] parameterValues = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = nameDiscoverer.getParameterNames(method);
        if (StringUtils.isEmpty(key)) {
            if (parameterNames != null && parameterNames.length > 0) {
                StringBuffer sb = new StringBuffer();
                int i = 0;

                for(int len = parameterNames.length; i < len; ++i) {
                    sb.append(parameterNames[i]).append(" = ").append(parameterValues[i]);
                }

                key = sb.toString();
            } else {
                key = "redissionLock";
            }

            return key;
        } else {
            SpelExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(key);
            if (parameterNames != null && parameterNames.length != 0) {
                EvaluationContext evaluationContext = new StandardEvaluationContext();

                for(int i = 0; i < parameterNames.length; ++i) {
                    evaluationContext.setVariable(parameterNames[i], parameterValues[i]);
                }

                try {
                    Object expressionValue = expression.getValue(evaluationContext);
                    return expressionValue != null && !"".equals(expressionValue.toString()) ? expressionValue.toString() : key;
                } catch (Exception var13) {
                    return key;
                }
            } else {
                return key;
            }
        }
    }
}*/
