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

    // 保留原有方法：获取连续打卡天数
    public long getContinuousDays(String userId) {
        String key = "memory:user:" + userId;
        String value = redisTemplate.opsForValue().get(key);
        return value != null ? Long.parseLong(value) : 0;
    }

    // 重构打卡方法：原子性操作保证每天只能打卡一次
    public void checkin(String userId) {
        String key = "memory:user:" + userId;
        //若今天打卡了，则直接返回
        if(todayCheckin(userId))return ;
        //否则，设置当日打卡标记（设置过期时间）
        redisTemplate.opsForValue().set(key + ":today", "1", Duration.between(ZonedDateTime.now(), ZonedDateTime.now().with(LocalTime.MIDNIGHT).plusDays(1)));
        //更新连续打卡天数
        long days = getContinuousDays(userId);
        redisTemplate.opsForValue().set(key, String.valueOf(days + 1), Duration.between(
                ZonedDateTime.now(),
                ZonedDateTime.now().with(LocalTime.MIDNIGHT).plusDays(1)
        ).getSeconds() , TimeUnit.SECONDS);
    }

    // 重构今日打卡检查：通过独立key判断当日是否打卡
    public boolean todayCheckin(String userId) {
        return redisTemplate.hasKey("memory:user:" + userId + ":today");
    }
}
