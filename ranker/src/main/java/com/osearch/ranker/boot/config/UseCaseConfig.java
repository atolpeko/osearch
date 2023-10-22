package com.osearch.ranker.boot.config;

import com.osearch.ranker.application.port.IndexRepository;
import com.osearch.ranker.application.port.PageRepository;
import com.osearch.ranker.application.usecase.RankerUseCase;
import com.osearch.ranker.application.usecase.RankerUseCaseImpl;
import com.osearch.ranker.domain.ranker.Ranker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public RankerUseCase rankerUseCase(
        Ranker topicRanker,
        Ranker metaRanker,
        Ranker pageRankRanker,
        Ranker finalRanker,
        PageRepository pageRepository,
        IndexRepository indexRepository
    ) {
        topicRanker.setNext(metaRanker);
        metaRanker.setNext(pageRankRanker);
        pageRankRanker.setNext(finalRanker);

        return new RankerUseCaseImpl(topicRanker, pageRepository, indexRepository);
    }
}
