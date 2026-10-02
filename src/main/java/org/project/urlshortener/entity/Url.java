package org.project.urlshortener.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "urls")
@Data
public class Url {
    @Id
    private String id;
    private String originalUrl;
    @Indexed(unique = true)
    private String shortCode;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
