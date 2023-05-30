package com.osearch.indexer.service.analyzer;

import static com.osearch.indexer.fixture.ContentAnalyzerFixture.page;
import static com.osearch.indexer.fixture.ContentAnalyzerFixture.request;
import static com.osearch.indexer.fixture.ContentAnalyzerFixture.stopWordsForEng;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("category.UnitTest")
class ContentAnalyzerImplTest {
    private ContentAnalyzerImpl target;

    @BeforeEach
    void setUp() {
        target = new ContentAnalyzerImpl(stopWordsForEng());
    }

    @Test
    void shouldAnalyze() {
        var page = target.analyze(request());
        var keywords = page.getKeywords();
        var meta = page.getMetaTags();
        page.setKeywords(null);
        page.setMetaTags(null);

        var expected = page();
        var expectedKeywords = expected.getKeywords();
        var expectedMeta = expected.getMetaTags();
        expected.setKeywords(null);
        expected.setMetaTags(null);

        assertEquals(expected, page);
        assertSetsIgnoringOrder(expectedKeywords, keywords);
        assertSetsIgnoringOrder(expectedMeta, meta);
    }

    <T> void assertSetsIgnoringOrder(Set<T> expected, Set<T> actual) {
        expected = new HashSet<>(expected);
        actual = new HashSet<>(actual);

        if (!expected.containsAll(actual)) {
            fail("Expected: " + expected + ", Actual: " + actual);
        }
    }
}