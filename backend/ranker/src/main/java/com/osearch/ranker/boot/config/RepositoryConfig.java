package com.osearch.ranker.boot.config;

import com.osearch.ranker.adapter.out.IndexRepositoryImpl;
import com.osearch.ranker.adapter.out.PageRepositoryImpl;
import com.osearch.ranker.adapter.out.jpa.IndexDtoJpaRepository;
import com.osearch.ranker.adapter.out.mapper.IndexMapper;
import com.osearch.ranker.adapter.out.mapper.PageMapper;
import com.osearch.ranker.application.port.IndexRepository;
import com.osearch.ranker.application.port.PageRepository;

import org.neo4j.driver.Driver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {

    @Bean
    public PageRepository pageRepository(Driver driver) {
        return new PageRepositoryImpl(driver);
    }

    @Bean
    public IndexRepository indexRepository(
        IndexDtoJpaRepository jpaRepository,
        IndexMapper indexMapper,
        PageMapper pageMapper
    ) {
        return new IndexRepositoryImpl(jpaRepository, indexMapper, pageMapper);
    }
}
