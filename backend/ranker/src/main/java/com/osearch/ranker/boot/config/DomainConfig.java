package com.osearch.ranker.boot.config;

import com.osearch.ranker.domain.properties.DomainProperties;
import com.osearch.ranker.domain.service.indexer.IndexImpl;
import com.osearch.ranker.domain.service.indexer.Indexer;
import com.osearch.ranker.domain.service.ranker.RankerService;
import com.osearch.ranker.domain.service.ranker.impl.KeywordRanker;
import com.osearch.ranker.domain.service.ranker.impl.MetaRanker;
import com.osearch.ranker.domain.service.ranker.impl.PageRankRanker;
import com.osearch.ranker.domain.service.ranker.impl.Ranker;
import com.osearch.ranker.domain.service.ranker.RankerServiceImpl;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public Indexer indexer() {
        return new IndexImpl();
    }

    @Bean
    public RankerService rankerService(
        List<Ranker> rankers,
        DomainProperties properties
    ) {
        return new RankerServiceImpl(rankers, properties);
    }

    @Bean
    public Ranker keywordRanker() {
        return new KeywordRanker();
    }

    @Bean
    public Ranker pageRankRanker(DomainProperties properties) {
        return new PageRankRanker(properties);
    }

    @Bean
    public Ranker metaRanker() {
        return new MetaRanker();
    }
}
