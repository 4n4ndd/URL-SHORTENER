package org.project.urlshortener.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

@Document(collection = "urls")
public class URL {
    @Id
    private Long id;
    private String originalUrl;
    private String shortCode;
    private LocalDateTime createdAt;
}
