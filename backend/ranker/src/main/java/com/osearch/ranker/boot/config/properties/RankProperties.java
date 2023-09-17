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

    @Value("${rank.keywordRankWeight}")
    private double keywordRankWeight;

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
    public double getKeywordRankWeight() {
        return keywordRankWeight;
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
