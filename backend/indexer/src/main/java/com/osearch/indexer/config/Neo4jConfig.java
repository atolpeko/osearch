package com.osearch.indexer.config;

import com.osearch.indexer.config.properties.NeoProperties;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Neo4jConfig extends Neo4jAutoConfiguration {

    @Autowired
    private NeoProperties properties;

    @Bean
    public Driver driver() {
        System.out.println("aaa");
        return GraphDatabase.driver(properties.getUrl(),
                AuthTokens.basic(properties.getUsername(), properties.getPassword()));
    }
}