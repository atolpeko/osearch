package com.osearch.ranker.boot.config.properties;

import com.osearch.ranker.domain.properties.DomainProperties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RankProperties implements DomainProperties {

    @Value("${rank.pageRankIterations}")
    private int pageRankIterations;

    @Value("${rank.pageRankDampingFactor}")
    private double pageRankDampingFactor;

    @Value("${rank.topicRankWeight}")
    private double topicRankWeight;

    @Value("${rank.pageRankWeight}")
    private double pageRankWeight;

    @Value("${rank.metaRankWeight}")
    private double metaRankWeight;

    @Override
    public int getPageRankIterations() {
        return pageRankIterations;
    }

    @Override
    public double getPageRankDampingFactor() {
        return pageRankDampingFactor;
    }

    @Override
    public double getTopicRankWeight() {
        return topicRankWeight;
    }

    @Override
    public double getPageRankWeight() {
        return pageRankWeight;
    }

    @Override
    public double getMetaRankWeight() {
        return metaRankWeight;
    }
}
