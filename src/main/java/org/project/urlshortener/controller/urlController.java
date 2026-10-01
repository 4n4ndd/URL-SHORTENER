package org.project.urlshortener.controller;

import lombok.RequiredArgsConstructor;
import org.project.urlshortener.dto.CreateUrlRequest;
import org.project.urlshortener.dto.CreateUrlResponse;
import org.project.urlshortener.service.urlService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class urlController {
    private final urlService urlService;
    @PostMapping("/url")
    public CreateUrlResponse addUrl(@RequestBody CreateUrlRequest request){
        return urlService.addUrl(request);
    }
}
