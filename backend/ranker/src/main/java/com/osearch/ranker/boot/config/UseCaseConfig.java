package com.osearch.ranker.boot.config;

import com.osearch.ranker.application.port.IndexRepository;
import com.osearch.ranker.application.port.PageRepository;
import com.osearch.ranker.application.usecase.RankerUseCase;
import com.osearch.ranker.application.usecase.RankerUseCaseImpl;
import com.osearch.ranker.domain.service.indexer.Indexer;
import com.osearch.ranker.domain.service.ranker.RankerService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public RankerUseCase rankerUseCase(
        Indexer indexer,
        RankerService rankerService,
        PageRepository pageRepository,
        IndexRepository indexRepository
    ) {
        return new RankerUseCaseImpl(indexer, rankerService, pageRepository, indexRepository);
    }
}
