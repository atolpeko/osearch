package com.osearch.indexer.domain.analyzer;

import static com.osearch.indexer.fixture.ContentAnalyzerFixture.CONTEXT;
import static com.osearch.indexer.fixture.ContentAnalyzerFixture.NEW_CONTEXT;
import static com.osearch.indexer.fixture.ContentAnalyzerFixture.QUOTE;
import static com.osearch.indexer.fixture.ContentAnalyzerFixture.TOPICS;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.osearch.indexer.config.IntegrationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

@IntegrationTest
@Tag("category.IntegrationTest")
class ContentAnalyzerTest {

    @Autowired
    @Qualifier("documentPreparer")
    ContentAnalyzer contentAnalyzer;

    @BeforeEach
    void analyze() {
        contentAnalyzer.analyze(CONTEXT);
    }

    @Test
    void shouldPrepareDocument() {
        assertNotNull(CONTEXT.getDocument());
    }

    @Test
    void shouldReplaceReferences() {
        assertEquals(NEW_CONTEXT.getContent(), CONTEXT.getContent());
    }

    @Test
    void shouldExtractTopics() {
        assertEquals(NEW_CONTEXT.getTopics(), CONTEXT.getTopics());
    }

    @Test
    void shouldExtractQuotes() {
        var quote = TOPICS.stream()
            .filter(topic -> topic.getMainSubject().equals(QUOTE))
            .findAny();
        assertTrue(quote.isPresent());
    }
}