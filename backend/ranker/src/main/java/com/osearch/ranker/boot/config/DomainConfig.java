package com.osearch.ranker.boot.config;

import com.osearch.ranker.domain.properties.DomainProperties;
import com.osearch.ranker.domain.service.FinalRanker;
import com.osearch.ranker.domain.service.TopicRanker;
import com.osearch.ranker.domain.service.MetaRanker;
import com.osearch.ranker.domain.service.PageRankRanker;
import com.osearch.ranker.domain.service.Ranker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public Ranker topicRanker() {
        return new TopicRanker();
    }

    @Bean
    public Ranker pageRankRanker(DomainProperties properties) {
        return new PageRankRanker(properties);
    }

    @Bean
    public Ranker metaRanker() {
        return new MetaRanker();
    }

    @Bean
    public Ranker finalRanker(DomainProperties properties) {
        return new FinalRanker(properties);
    }
}
