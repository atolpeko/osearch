package com.osearch.search.boot.config;

import com.osearch.search.domain.analyzer.SearchStringAnalyzer;
import com.osearch.search.domain.analyzer.SearchStringAnalyzerImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public SearchStringAnalyzer searchStringAnalyzer() {
        return new SearchStringAnalyzerImpl();
    }
}
