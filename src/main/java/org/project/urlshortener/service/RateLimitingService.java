package org.project.urlshortener.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RateLimitingService {
    private final RedisTemplate<String,String> redisTemplate;
    private static final int MAX_REQUESTS = 10;
    private static final long WINDOW_SECONDS = 60;
    public boolean isAllowed(String clientIp) {

        String key = "rate_limit:" + clientIp;
        Long requestCount = redisTemplate.opsForValue().increment(key);

        if (requestCount != null && requestCount == 1) {
            redisTemplate.expire(key, WINDOW_SECONDS, TimeUnit.SECONDS);
        }
        return requestCount != null && requestCount <= MAX_REQUESTS;
    }
}
