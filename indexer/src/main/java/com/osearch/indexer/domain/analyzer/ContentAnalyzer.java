package com.osearch.indexer.domain.analyzer;

import com.osearch.indexer.domain.exception.AnalyzerException;
import com.osearch.indexer.domain.valueobject.AnalyzerContext;

/**
 * Interface for analyzing content using a chain of ContentAnalyzers.
 */
public interface ContentAnalyzer {

    /**
     * Analyzes the provided AnalyzerContext.
     *
     * @param context The context containing the data to be analyzed.
     *
     * @throws AnalyzerException if an error happens during content analysis.
     */
    void analyze(AnalyzerContext context);

    /**
     * Sets the next ContentAnalyzer in the chain.
     *
     * @param next The next ContentAnalyzer instance to be set.
     */
    void setNext(ContentAnalyzer next);
}
