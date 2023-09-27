package com.osearch.indexer.domain.valueobject;

import com.osearch.indexer.domain.entity.Topic;

import edu.stanford.nlp.pipeline.CoreDocument;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

/**
 * The AnalyzerContext class represents the context for analyzing text.
 */
@Data
@Builder
public class AnalyzerContext {
    private String id;
    private CoreDocument document;
    private String content;
    private Set<Topic> topics;
}
