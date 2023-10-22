package com.osearch.indexer.boot.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.indexer.adapter.in.messaging.mapper.IndexRequestMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public IndexRequestMapper indexRequestMapper(ObjectMapper mapper) {
        return new IndexRequestMapper(mapper);
    }
}
