package com.osearch.search.domain.analyzer;

import com.osearch.search.domain.exception.AnalyzerException;
import com.osearch.search.domain.valueobject.Topics;

/**
 * The SearchStringAnalyzer interface provides a method to analyze
 * a search string and extract topic.
 */
public interface SearchStringAnalyzer {

    /**
     * Analyzes the given search string and extracts topic.
     *
     * @param searchString the string to be analyzed.
     * @return topics extracted.
     *
     * @throws AnalyzerException if an error happens during search string analysis.
     */
    Topics analyze(String searchString);
}
