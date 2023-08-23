package com.osearch.crawler.boot.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.crawler.adapter.in.messaging.mapper.RequestMapper;
import com.osearch.crawler.adapter.in.messaging.validator.RequestValidator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public RequestMapper requestMapper(
        ObjectMapper mapper,
        RequestValidator validator
    ) {
        return new RequestMapper(mapper, validator);
    }
}
