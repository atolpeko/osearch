package com.osearch.ranker.domain.properties;

/**
 * Domain properties.
 */
public interface DomainProperties {

    int getPageRankIterations();
    double getPageRankDampingFactor();
    double getKeywordRankWeight();
    double getPageRankWeight();
    double getMetaRankWeight();
}
