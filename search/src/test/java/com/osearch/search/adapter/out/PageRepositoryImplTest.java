package com.osearch.search.adapter.out;

import static com.osearch.search.fixture.PageRepositoryFixture.LIMIT;
import static com.osearch.search.fixture.PageRepositoryFixture.OFFSET;
import static com.osearch.search.fixture.PageRepositoryFixture.TOPIC_1;
import static com.osearch.search.fixture.PageRepositoryFixture.TOPIC_1_PAGES;
import static com.osearch.search.fixture.PageRepositoryFixture.TOPIC_2;
import static com.osearch.search.fixture.PageRepositoryFixture.TOPIC_2_PAGES;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.osearch.search.config.IntegrationTest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@Tag("category.IntegrationTest")
class PageRepositoryImplTest {

    @Autowired
    PageRepositoryImpl repository;

    @Test
    void shouldFindPagesForTopics() {
        var pages = repository.findAllByTopic(TOPIC_1, OFFSET, LIMIT);
        assertEquals(TOPIC_1_PAGES, pages);
    }

    @Test
    void shouldCountPagesForTopics() {
        var pages = repository.countForTopic(TOPIC_2);
        assertEquals(TOPIC_2_PAGES.size(), pages);
    }
}