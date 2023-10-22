package com.osearch.search.domain.analyzer;

import static com.osearch.search.fixture.SearchStringAnalyzerFixture.SEARCH_STRING;
import static com.osearch.search.fixture.SearchStringAnalyzerFixture.TOPICS;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("category.UnitTest")
class SearchStringAnalyzerImplTest {
    SearchStringAnalyzerImpl target;

    @BeforeEach
    void setUp() {
        target = new SearchStringAnalyzerImpl();
    }

    @Test
    void shouldAnalyze() {
        var topics = target.analyze(SEARCH_STRING);
        assertEquals(TOPICS, topics);
    }
}