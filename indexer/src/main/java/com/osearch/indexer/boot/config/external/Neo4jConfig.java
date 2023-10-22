package com.osearch.indexer.boot.config.external;

import com.osearch.indexer.boot.config.properties.NeoProperties;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

import org.springframework.boot.autoconfigure.neo4j.Neo4jAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"debug", "local", "prod"})
public class Neo4jConfig extends Neo4jAutoConfiguration {

    @Bean
    public Driver driver(NeoProperties properties) {
        return GraphDatabase.driver(
            properties.getUrl(),
            AuthTokens.basic(properties.getUsername(), properties.getPassword())
        );
    }
}
