package com.totoo.system.service;

import org.springframework.data.redis.core.StringRedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

class RedisServiceTest {
    private final StringRedisTemplate redisTemplate=new StringRedisTemplate();
    @org.junit.jupiter.api.Test
    void getContinuousDays() {
    }

    @org.junit.jupiter.api.Test
    void increase() {
    }

    @org.junit.jupiter.api.Test
    void setCheckinPattern() {
    }

    @org.junit.jupiter.api.Test
    void getCheckinPattern() {
    }
    @org.junit.jupiter.api.Test
    void TestBase(){
        redisTemplate.hasKey("user:1:checkin_pattern:learnNew");
    }
}