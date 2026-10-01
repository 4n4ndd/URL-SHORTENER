package org.project.urlshortener.service;

import lombok.RequiredArgsConstructor;
import org.project.urlshortener.dto.CreateUrlRequest;
import org.project.urlshortener.dto.CreateUrlResponse;
import org.project.urlshortener.entity.Url;
import org.project.urlshortener.exception.UrlNotFoundException;
import org.project.urlshortener.repository.urlRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class urlService {

    private final urlRepo urlRepository;

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 6;

    public CreateUrlResponse addUrl(CreateUrlRequest request) {
        String shortCode = generateUniqueShortCode();
        Url url = new Url();
        url.setOriginalUrl(request.getOriginalUrl());
        url.setShortCode(shortCode);
        url.setCreatedAt(LocalDateTime.now());

        urlRepository.save(url);
        String shortUrl = "https://localhost:8080/" + shortCode;
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
        Url url = urlRepository.findByShortCode(shortCode).orElseThrow(()->new UrlNotFoundException("Url not found"));
        return url.getOriginalUrl();
    }
}
