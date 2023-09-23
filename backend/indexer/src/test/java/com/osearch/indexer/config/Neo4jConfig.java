package com.osearch.indexer.config;

import static org.mockito.Mockito.mock;

import org.neo4j.driver.Driver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
class Neo4jConfig {

    @Bean
    public Driver driver() {
        return mock(Driver.class);
    }
}
