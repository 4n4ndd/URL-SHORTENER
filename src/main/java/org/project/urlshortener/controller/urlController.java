package org.project.urlshortener.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.project.urlshortener.dto.CreateUrlRequest;
import org.project.urlshortener.dto.CreateUrlResponse;
import org.project.urlshortener.service.RateLimitingService;
import org.project.urlshortener.service.urlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class urlController {
    private final urlService urlService;
    private final RateLimitingService rateLimitingService;
    @PostMapping("/url")
    public CreateUrlResponse addUrl(@RequestBody CreateUrlRequest request, HttpServletRequest httpRequest) {
        String clientIp = httpRequest.getRemoteAddr();
        if (!rateLimitingService.isAllowed(clientIp)) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Rate limit exceeded");
        }

        return urlService.addUrl(request);
    }
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
    }
}
