package org.project.urlshortener.service;

import lombok.RequiredArgsConstructor;
import org.project.urlshortener.dto.CreateUrlRequest;
import org.project.urlshortener.dto.CreateUrlResponse;
import org.project.urlshortener.entity.Url;
import org.project.urlshortener.exception.UrlNotFoundException;
import org.project.urlshortener.repository.urlRepo;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class urlService {

    private final urlRepo urlRepository;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;

    public CreateUrlResponse addUrl(CreateUrlRequest request) {
        String shortCode = generateUniqueShortCode();
        Url url = new Url();
        url.setOriginalUrl(request.getOriginalUrl());
        url.setShortCode(shortCode);
        url.setCreatedAt(LocalDateTime.now());
        if (request.getExpirationHours() != null) {
            url.setExpiresAt(LocalDateTime.now().plusHours(request.getExpirationHours()));
        }

        urlRepository.save(url);
        String shortUrl = "https://localhost:8081/" + shortCode;
        return new CreateUrlResponse(shortUrl);
    }

    private String generateUniqueShortCode() {
        String shortCode;
        do{
            shortCode = generateShortCode();
        }
        while (urlRepository.existsByShortCode(shortCode));
            return shortCode;
    }

    private String generateShortCode() {
        StringBuilder shortCode = new StringBuilder();
        for(int i = 0; i<CODE_LENGTH; i++){
            int randomIndex = ThreadLocalRandom.current().nextInt(CHARACTERS.length());
            shortCode.append(CHARACTERS.charAt(randomIndex));
        }
        return shortCode.toString();
    }

    public String getOriginalUrl(String shortCode) {
        String cacheKey = "url:" + shortCode;
        String cachedUrl = redisTemplate.opsForValue().get(cacheKey);

        if (cachedUrl != null) {
            return cachedUrl;
        }
        Url url = urlRepository.findByShortCode(shortCode).orElseThrow(()->new UrlNotFoundException("Url not found"));
        if (url.getExpiresAt() != null) {
            Duration duration = Duration.between(LocalDateTime.now(), url.getExpiresAt());
            redisTemplate.opsForValue().set(cacheKey, url.getOriginalUrl(), duration);

        } else {
            redisTemplate.opsForValue().set(cacheKey, url.getOriginalUrl());
        }
        return url.getOriginalUrl();
    }
}
