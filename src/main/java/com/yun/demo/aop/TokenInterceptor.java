/*
package com.yun.demo.aop;


import com.yun.demo.annotation.NotRepeatSubmit;
import com.yun.demo.util.ApiUtil;
import com.yun.demo.util.MD5Util;
import com.yun.demo.vo.TokenInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.Assert;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

*/
/**
 * @author zxy
 *//*

//@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String timestamp = request.getHeader("timestamp");
        // 随机字符串
        String nonce = request.getHeader("nonce");
        String sign = request.getHeader("sign");
        Assert.isTrue(!StringUtils.isEmpty(token) && !StringUtils.isEmpty(timestamp) && !StringUtils.isEmpty(sign), "参数错误");

        // 获取超时时间
        NotRepeatSubmit notRepeatSubmit = ApiUtil.getNotRepeatSubmit(handler);
        long expireTime = notRepeatSubmit == null ? 5 * 60 * 1000 : notRepeatSubmit.value();


        // 2. 请求时间间隔
        long reqeustInterval = System.currentTimeMillis() - Long.valueOf(timestamp);
        Assert.isTrue(reqeustInterval < expireTime, "请求超时，请重新请求");


        // 3. 校验Token是否存在
        ValueOperations<String, TokenInfo> tokenRedis = redisTemplate.opsForValue();
        TokenInfo tokenInfo = tokenRedis.get(token);
        Assert.notNull(tokenInfo, "token错误");


        // 4. 校验签名(将所有的参数加进来，防止别人篡改参数) 所有参数看参数名升续排序拼接成url
        // 请求参数 + token + timestamp + nonce
        String signString = ApiUtil.concatSignString(request) + tokenInfo.getAppInfo().getKey() + token + timestamp + nonce;
        String signature = MD5Util.encode(signString);
        boolean flag = signature.equals(sign);
        Assert.isTrue(flag, "签名错误");


        // 5. 拒绝重复调用(第一次访问时存储，过期时间和请求超时时间保持一致), 只有标注不允许重复提交注解的才会校验
        if (notRepeatSubmit != null) {
            ValueOperations<String, Integer> signRedis = redisTemplate.opsForValue();
            boolean exists = redisTemplate.hasKey(sign);
            Assert.isTrue(!exists, "请勿重复提交");
            signRedis.set(sign, 0, expireTime, TimeUnit.MILLISECONDS);
        }

        return super.preHandle(request, response, handler);
    }

//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
//        Object handler) throws Exception {
//        String authToken = request.getHeader("Authorization");
//        String token = authToken.substring("Bearer".length() + 1).trim();
//        UserTokenDTO userTokenDTO = JWTUtil.parseToken(token);
//        //1.判断请求是否有效
//        if (redisService.get(userTokenDTO.getUserId()) == null
//            || !redisService.get(userTokenDTO.getUserId()).equals(token)) {
//            return false;
//        }
//
//        //2.判断是否需要续期
//        if (redisService.getExpireTime(userTokenDTO.getUserId()) < 1 * 60 * 30) {
//            redisService.set(userTokenDTO.getUserId(), token);
//            log.error("update token info, id is:{}, user info is:{}", userTokenDTO.getUserId(), token);
//        }
//        return true;
//    }
}*/
