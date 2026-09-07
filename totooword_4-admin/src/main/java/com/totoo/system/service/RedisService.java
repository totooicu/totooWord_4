package com.totoo.system.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    // 获取用户的连续打卡天数，如果不存在则返回0
    public long getContinuousDays(String userId) {
        String key = "user:" + userId + ":streak";
        String value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return 0;
        }
        return Long.parseLong(value);
    }


    // 增加用户的连续打卡天数，并返回自增后的值
    public long increase(String userId) {
        String key = "user:" + userId + ":streak";
        long newValue = redisTemplate.opsForValue().increment(key);
        // 设置过期时间为明天0点
        redisTemplate.expire(key, Duration.between(ZonedDateTime.now(), ZonedDateTime.now().with(LocalTime.MIDNIGHT).plusDays(1)).getSeconds(), TimeUnit.SECONDS);
        return newValue;
    }
    //如果两种模式都打卡返回true
    public boolean setCheckinPattern(String userId, String pattern) {
        String key = "user:" + userId + ":checkin_pattern:"+pattern;
        if(redisTemplate.opsForValue().get("user:" + userId + ":checkin_pattern:"+"review") != null&&
                redisTemplate.opsForValue().get("user:" + userId + ":checkin_pattern:"+"learnNew") != null)return true;
        redisTemplate.opsForValue().set(key, pattern);
        // 设置过期时间为明天0点
        redisTemplate.expire(key, Duration.between(ZonedDateTime.now(), ZonedDateTime.now().with(LocalTime.MIDNIGHT).plusDays(1)).getSeconds(), TimeUnit.SECONDS);
        if(redisTemplate.opsForValue().get("user:" + userId + ":checkin_pattern:"+"review") != null&&
                redisTemplate.opsForValue().get("user:" + userId + ":checkin_pattern:"+"learnNew") != null){
            increase(userId);
            return true;
        }else return false;
    }
    public boolean getCheckinPattern(String userId, String pattern) {
        String key = "user:" + userId + ":checkin_pattern:"+pattern;
        System.out.println(">>>getCheckinPattern:"+ (redisTemplate.opsForValue().get(key) ));
        System.out.println(">>>getCheckinPattern:"+ (redisTemplate.hasKey(key) ));
        return redisTemplate.opsForValue().get(key) != null;

    }
}
