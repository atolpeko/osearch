package com.osearch.crawler.boot.config;

import com.osearch.crawler.adapter.out.repository.PageRepositoryImpl;
import com.osearch.crawler.adapter.out.repository.jpa.PageDtoJpaRepository;
import com.osearch.crawler.adapter.out.repository.mapper.PageMapper;
import com.osearch.crawler.application.port.PageRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public PageRepository pageRepository(
        PageDtoJpaRepository jpaRepository,
        PageMapper mapper
    ) {
        return new PageRepositoryImpl(jpaRepository, mapper);
    }
}
