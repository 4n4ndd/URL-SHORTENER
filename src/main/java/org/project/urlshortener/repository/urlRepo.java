package org.project.urlshortener.repository;

import org.project.urlshortener.entity.URL;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface urlRepo extends MongoRepository<URL,Long> {
}
