package com.osearch.search.boot.config;

import com.osearch.search.adapter.out.IndexRepositoryImpl;
import com.osearch.search.adapter.out.PageRepositoryImpl;
import com.osearch.search.application.port.IndexRepository;
import com.osearch.search.application.port.PageRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class RepositoryConfig {

    @Bean
    public PageRepository pageRepository(JdbcTemplate jdbcTemplate) {
        return new PageRepositoryImpl(jdbcTemplate);
    }

    @Bean
    public IndexRepository indexRepository(JdbcTemplate jdbcTemplate) {
        return new IndexRepositoryImpl(jdbcTemplate);
    }
}
