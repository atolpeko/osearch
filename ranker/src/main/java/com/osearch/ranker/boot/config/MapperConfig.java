package com.osearch.ranker.boot.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.ranker.adapter.in.mapper.RequestMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RequestMapper requestMapper(ObjectMapper mapper) {
        return new RequestMapper(mapper);
    }
}
