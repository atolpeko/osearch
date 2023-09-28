package com.osearch.search.boot.config;

import com.osearch.search.adapter.in.mapper.IndexInfoMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public IndexInfoMapper indexInfoMapper() {
        return new IndexInfoMapper();
    }
}
