package com.osearch.indexer.domain;

import static com.osearch.indexer.fixture.IndexServiceFixture.EXTRACTED_TOPICS;
import static com.osearch.indexer.fixture.IndexServiceFixture.PAGE;
import static com.osearch.indexer.fixture.IndexServiceFixture.REQUEST;
import static com.osearch.indexer.fixture.IndexServiceFixture.REQUEST_UNSUPPORTED_LOCALE;
import static com.osearch.indexer.fixture.IndexServiceFixture.SUPPORTED_LOCALES;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.osearch.indexer.domain.analyzer.ContentAnalyzer;
import com.osearch.indexer.domain.exception.AnalyzerException;
import com.osearch.indexer.domain.valueobject.AnalyzerContext;
import com.osearch.indexer.domain.exception.UnsupportedLocaleException;
import com.osearch.indexer.domain.valueobject.SupportedLocales;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class IndexServiceImplTest {

    @InjectMocks
    IndexServiceImpl target;

    @Mock
    ContentAnalyzer analyzer;

    @Mock
    SupportedLocales locales;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(locales.get()).thenReturn(SUPPORTED_LOCALES);
        doAnswer(invocation -> {
            var context = invocation.getArgument(0, AnalyzerContext.class);
            context.setTopics(new HashSet<>(EXTRACTED_TOPICS));
            return null;
        }).when(analyzer).analyze(any());
    }

    @Test
    void shouldIndex() {
        var page = target.index(REQUEST);
        assertEquals(PAGE, page);
    }

    @Test
    void shouldThrowExceptionWhenLocaleIsNotSupported() {
        assertThrows(UnsupportedLocaleException.class,
            () -> target.index(REQUEST_UNSUPPORTED_LOCALE));
    }

    @Test
    void shouldThrowExceptionWhenAnalyzerFails() {
        doThrow(AnalyzerException.class).when(analyzer).analyze(any());
        assertThrows(AnalyzerException.class,
            () -> target.index(REQUEST));
    }
}