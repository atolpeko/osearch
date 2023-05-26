package com.osearch.crawler.inout.repository;

import com.osearch.crawler.inout.repository.dto.URLDto;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Used to work with URLDto objects stored in mongo.
 */
@Repository
public interface URLRepository extends MongoRepository<URLDto, String> {

    /**
     * Find URLDto by its hash
     *
     * @param hash  hash of the URLDto to find
     *
     * @return optional of found URLDto or Optional.empty()
     */
    Optional<URLDto> findByUrlHash(String hash);
}
