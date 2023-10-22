package com.osearch.indexer.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.neo4j.driver.Driver;

import org.neo4j.driver.Session;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
class Neo4jConfig {

    @Bean
    public Driver driver() {
        var driver = mock(Driver.class);
        var session = mock(Session.class);
        when(driver.session()).thenReturn(session);
        when(session.readTransaction(any())).thenReturn(1);
        when(session.writeTransaction(any())).thenReturn(1);

        return driver;
    }
}
