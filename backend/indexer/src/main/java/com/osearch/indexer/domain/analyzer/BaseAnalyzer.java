package com.osearch.indexer.domain.analyzer;

import com.osearch.indexer.domain.entity.AnalyzerContext;

import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * A base analyzer class.
 * To create a concrete analyzer, extend this class and implement the analyze method.
 */
@Setter
@RequiredArgsConstructor
public abstract class BaseAnalyzer implements ContentAnalyzer {
    private ContentAnalyzer next;

    /**
     * Calls the `analyze` method of the next Analyzer in the chain, if it exists.
     *
     * @param context the AnalyzerContext object to be passed to the next Analyzer
     */
    protected void next(AnalyzerContext context) {
        if (next != null) {
            next.analyze(context);
        }
    }
}
