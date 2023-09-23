package com.osearch.indexer.boot.config;

import com.osearch.indexer.application.port.PageMessageSender;
import com.osearch.indexer.application.port.PageRepository;
import com.osearch.indexer.application.usecase.IndexerUseCase;
import com.osearch.indexer.application.usecase.IndexerUseCaseImpl;
import com.osearch.indexer.domain.IndexService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public IndexerUseCase indexerUseCase(
        IndexService indexService,
        PageRepository repository,
        PageMessageSender messageSender
    ) {
        return new IndexerUseCaseImpl(indexService, repository, messageSender);
    }
}
