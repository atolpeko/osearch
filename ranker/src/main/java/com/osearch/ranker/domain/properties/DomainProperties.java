package com.osearch.ranker.domain.properties;

/**
 * Domain properties.
 */
public interface DomainProperties {

    /**
     * Returns the number of iterations used for the PageRank algorithm.
     *
     * @return The number of iterations for the PageRank algorithm.
     */
    int getPageRankIterations();

    /**
     * Returns the damping factor used for the PageRank algorithm.
     *
     * @return The damping factor used for the PageRank algorithm.
     */
    double getPageRankDampingFactor();

    /**
     * Returns the weight assigned to the topic rank in the algorithm.
     *
     * @return The weight assigned to the topic rank.
     */
    double getTopicRankWeight();

    /**
     * Returns the weight assigned to the PageRank in the algorithm.
     *
     * @return The weight assigned to the PageRank.
     */
    double getPageRankWeight();

    /**
     * Returns the weight assigned to the MetaRank in the algorithm.
     *
     * @return The weight assigned to the MetaRank.
     */
    double getMetaRankWeight();
}
