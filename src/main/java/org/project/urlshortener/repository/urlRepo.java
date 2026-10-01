package org.project.urlshortener.repository;

import org.project.urlshortener.entity.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface urlRepo extends MongoRepository<Url,Long> {
    boolean existsByShortCode(String shortCode);

    Optional<Url> findByShortCode(String shortCode);
}
