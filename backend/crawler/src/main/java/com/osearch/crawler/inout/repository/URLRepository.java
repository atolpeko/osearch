package com.osearch.crawler.inout.repository;

import com.osearch.crawler.inout.repository.dto.URLDto;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface URLRepository extends MongoRepository<URLDto, String> {

    Optional<URLDto> findByUrlHash(String hash);
}
