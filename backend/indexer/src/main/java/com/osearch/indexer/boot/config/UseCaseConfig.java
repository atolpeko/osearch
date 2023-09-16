package com.osearch.indexer.boot.config;

import com.osearch.indexer.application.port.PageMessageSender;
import com.osearch.indexer.application.port.PageRepository;
import com.osearch.indexer.application.usecase.IndexerUseCase;
import com.osearch.indexer.application.usecase.IndexerUseCaseImpl;
import com.osearch.indexer.domain.analyzer.ContentAnalyzer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public IndexerUseCase indexerUseCase(
        ContentAnalyzer analyzer,
        PageRepository repository,
        PageMessageSender messageSender
    ) {
        return new IndexerUseCaseImpl(analyzer, repository, messageSender);
    }
}
