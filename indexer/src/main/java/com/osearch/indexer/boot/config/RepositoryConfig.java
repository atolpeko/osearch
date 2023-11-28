package com.osearch.indexer.boot.config;

import com.osearch.indexer.adapter.out.repository.PageNeo4jRepository;
import com.osearch.indexer.adapter.out.repository.PageNeptuneRepository;
import com.osearch.indexer.application.port.PageRepository;

import org.neo4j.driver.Driver;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RepositoryConfig {

    @Bean
    @Profile("aws")
    public PageRepository pageNeptuneRepository(
        @Qualifier("readDriver") Driver readDriver,
        @Qualifier("writeDriver") Driver writeDriver
    ) {
        return new PageNeptuneRepository(readDriver, writeDriver);
    }

    @Bean
    @Profile({"test", "debug", "local", "prod"})
    public PageRepository pageNeo4JRepository(Driver driver) {
        return new PageNeo4jRepository(driver);
    }
}
