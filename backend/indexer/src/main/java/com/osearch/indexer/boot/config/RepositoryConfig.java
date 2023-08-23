package com.osearch.indexer.boot.config;

import com.osearch.indexer.adapter.out.PageRepositoryImpl;
import com.osearch.indexer.application.port.PageRepository;

import org.neo4j.driver.Driver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public PageRepository pageRepository(Driver driver) {
        return new PageRepositoryImpl(driver);
    }
}
