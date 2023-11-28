package com.osearch.ranker.boot.config;

import com.osearch.ranker.adapter.out.IndexRepositoryImpl;
import com.osearch.ranker.adapter.out.PageGraphRepository;
import com.osearch.ranker.application.port.IndexRepository;
import com.osearch.ranker.application.port.PageRepository;

import org.neo4j.driver.Driver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class RepositoryConfig {

    @Bean
    public PageRepository pageRepository(Driver driver) {
        return new PageGraphRepository(driver);
    }

    @Bean
    public IndexRepository indexRepository(JdbcTemplate template) {
        return new IndexRepositoryImpl(template);
    }
}
