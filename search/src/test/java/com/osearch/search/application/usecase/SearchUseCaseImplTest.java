package com.osearch.search.application.usecase;

import static com.osearch.search.fixture.SearchUseCaseFixture.LAST_MAIN_PAGE;
import static com.osearch.search.fixture.SearchUseCaseFixture.LIMIT;
import static com.osearch.search.fixture.SearchUseCaseFixture.LIMIT_PLUS_1;
import static com.osearch.search.fixture.SearchUseCaseFixture.MAIN_TOPIC;
import static com.osearch.search.fixture.SearchUseCaseFixture.MAIN_TOPICS;
import static com.osearch.search.fixture.SearchUseCaseFixture.OFFSET_FOR_MAIN_TOPIC;
import static com.osearch.search.fixture.SearchUseCaseFixture.MAIN_PAGEABLE;
import static com.osearch.search.fixture.SearchUseCaseFixture.MAIN_PAGES_PLUS_1;
import static com.osearch.search.fixture.SearchUseCaseFixture.OFFSET_FOR_MAIN_WITH_SIDE;
import static com.osearch.search.fixture.SearchUseCaseFixture.OFFSET_FOR_SIDE_TOPIC;
import static com.osearch.search.fixture.SearchUseCaseFixture.PAGEABLE_WITH_SIDE;
import static com.osearch.search.fixture.SearchUseCaseFixture.SEARCH_STRING;
import static com.osearch.search.fixture.SearchUseCaseFixture.SIDE_PAGES;
import static com.osearch.search.fixture.SearchUseCaseFixture.SIDE_TOPIC_1;
import static com.osearch.search.fixture.SearchUseCaseFixture.TOPICS_WITH_SIDE;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import com.osearch.search.application.port.PageRepository;
import com.osearch.search.domain.analyzer.SearchStringAnalyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class SearchUseCaseImplTest {

    @InjectMocks
    SearchUseCaseImpl target;

    @Mock
    PageRepository repository;

    @Mock
    SearchStringAnalyzer analyzer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldSearchForPages() {
        when(analyzer.analyze(SEARCH_STRING)).thenReturn(MAIN_TOPICS);
        when(repository.findAllByTopic(
            MAIN_TOPIC,
            OFFSET_FOR_MAIN_TOPIC,
            LIMIT_PLUS_1)
        ).thenReturn(MAIN_PAGES_PLUS_1);

        var pages = target.search(SEARCH_STRING, OFFSET_FOR_MAIN_TOPIC, LIMIT);
        assertEquals(MAIN_PAGEABLE, pages);
    }

    @Test
    void shouldSearchForPagesWithSideTopics() {
        when(analyzer.analyze(SEARCH_STRING)).thenReturn(TOPICS_WITH_SIDE);
        when(repository.findAllByTopic(
            MAIN_TOPIC,
            OFFSET_FOR_MAIN_WITH_SIDE,
            LIMIT_PLUS_1)
        ).thenReturn(LAST_MAIN_PAGE);
        when(repository.findAllByTopic(
            SIDE_TOPIC_1,
            OFFSET_FOR_SIDE_TOPIC,
            LIMIT)
        ).thenReturn(SIDE_PAGES);

        var pages = target.search(SEARCH_STRING, OFFSET_FOR_MAIN_WITH_SIDE, LIMIT);
        assertEquals(PAGEABLE_WITH_SIDE, pages);
    }
}