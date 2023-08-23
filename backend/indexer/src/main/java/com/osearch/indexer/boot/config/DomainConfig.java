package com.osearch.indexer.boot.config;

import com.osearch.indexer.domain.analyzer.ContentAnalyzer;
import com.osearch.indexer.domain.analyzer.ContentAnalyzerImpl;
import com.osearch.indexer.domain.entity.StopWords;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public ContentAnalyzer contentAnalyzer(StopWords stopWords) {
        return new ContentAnalyzerImpl(stopWords);
    }
}
