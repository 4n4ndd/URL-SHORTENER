package org.project.urlshortener.controller;

import lombok.RequiredArgsConstructor;
import org.project.urlshortener.dto.CreateUrlRequest;
import org.project.urlshortener.dto.CreateUrlResponse;
import org.project.urlshortener.service.urlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class urlController {
    private final urlService urlService;
    @PostMapping("/url")
    public CreateUrlResponse addUrl(@RequestBody CreateUrlRequest request){
        return urlService.addUrl(request);
    }
    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
    }
}
