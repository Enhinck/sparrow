package com.enhinck.sparrow.common.config;

import com.enhinck.sparrow.common.redis.FastJsonRedisSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class RedisConfig {

    @Autowired(required = false)
    RedisTemplate redisTemplate;

    @PostConstruct
    public void reconfig() {
        if (redisTemplate != null) {
            redisTemplate.setEnableDefaultSerializer(false);
            redisTemplate.setEnableTransactionSupport(false);
            redisTemplate.setKeySerializer(new StringRedisSerializer());
            redisTemplate.setValueSerializer(new FastJsonRedisSerializer());
            redisTemplate.setHashKeySerializer(new StringRedisSerializer());
            redisTemplate.setHashValueSerializer(new FastJsonRedisSerializer());
        }
    }
}
