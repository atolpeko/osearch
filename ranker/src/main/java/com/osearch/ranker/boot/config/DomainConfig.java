package com.osearch.ranker.boot.config;

import com.osearch.ranker.domain.properties.DomainProperties;
import com.osearch.ranker.domain.ranker.FinalRanker;
import com.osearch.ranker.domain.ranker.TopicRanker;
import com.osearch.ranker.domain.ranker.MetaRanker;
import com.osearch.ranker.domain.ranker.PageRankRanker;
import com.osearch.ranker.domain.ranker.Ranker;

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
