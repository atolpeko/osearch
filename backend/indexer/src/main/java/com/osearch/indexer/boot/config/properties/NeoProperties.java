package com.osearch.indexer.boot.config.properties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class NeoProperties {

    @Value("${neo4j.url}")
    private String url;

    @Value("${neo4j.username}")
    private String username;

    @Value("${neo4j.password}")
    private String password;
}
