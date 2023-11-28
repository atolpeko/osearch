package com.osearch.crawler.adapter.out.repository.mapper;

import static com.osearch.crawler.fixture.PageDynamoDbMapperFixture.EMPTY_PAGE_RESPONSE;
import static com.osearch.crawler.fixture.PageDynamoDbMapperFixture.PAGE;
import static com.osearch.crawler.fixture.PageDynamoDbMapperFixture.PRESENT_PAGE_RESPONSE;
import static com.osearch.crawler.fixture.PageDynamoDbMapperFixture.TABLE_NAME;
import static com.osearch.crawler.fixture.PageDynamoDbMapperFixture.putItemRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("category.UnitTest")
class PageDynamoDbMapperTest {
    PageDynamoDbMapper target;

    @BeforeEach
    void setUp() {
        target = new PageDynamoDbMapper();
    }

    @Test
    void shouldConvertWhenPageIsPresent() {
        var page = target.toPage(PRESENT_PAGE_RESPONSE);
        assertEquals(PAGE, page.get());
    }

    @Test
    void shouldReturnOptionalEmptyWhenPageIsNotPresent() {
        var page = target.toPage(EMPTY_PAGE_RESPONSE);
        assertTrue(page.isEmpty());
    }

    @Test
    void shouldConvertPageIntoPutItemRequest() {
        var request = target.toRequest(PAGE, TABLE_NAME);
        assertEquals(putItemRequest(), request);
    }
}
