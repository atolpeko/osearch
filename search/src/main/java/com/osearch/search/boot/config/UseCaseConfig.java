package com.osearch.search.boot.config;

import com.osearch.search.application.port.IndexRepository;
import com.osearch.search.application.port.PageRepository;
import com.osearch.search.application.usecase.IndexUseCase;
import com.osearch.search.application.usecase.IndexUseCaseImpl;
import com.osearch.search.application.usecase.SearchUseCase;
import com.osearch.search.application.usecase.SearchUseCaseImpl;
import com.osearch.search.domain.analyzer.SearchStringAnalyzer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public SearchUseCase searchUseCase(
        SearchStringAnalyzer analyzer,
        PageRepository pageRepository
    ) {
        return new SearchUseCaseImpl(analyzer, pageRepository);
    }

    @Bean
    public IndexUseCase indexUseCase(IndexRepository repository) {
        return new IndexUseCaseImpl(repository);
    }
}
