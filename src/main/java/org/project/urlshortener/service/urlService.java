package org.project.urlshortener.service;

import lombok.RequiredArgsConstructor;
import org.project.urlshortener.dto.CreateUrlRequest;
import org.project.urlshortener.dto.CreateUrlResponse;
import org.project.urlshortener.entity.URL;
import org.project.urlshortener.repository.urlRepo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class urlService {
    private final urlRepo urlRepo;

    public CreateUrlResponse addUrl(CreateUrlRequest request) {

    }
}
