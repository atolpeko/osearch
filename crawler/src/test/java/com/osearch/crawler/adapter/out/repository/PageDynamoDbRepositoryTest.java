package com.osearch.crawler.adapter.out.repository;

import static com.osearch.crawler.fixture.PageDynamoDbRepositoryFixture.COUNT_REQUEST;
import static com.osearch.crawler.fixture.PageDynamoDbRepositoryFixture.COUNT_RESPONSE;
import static com.osearch.crawler.fixture.PageDynamoDbRepositoryFixture.EMPTY_PAGE_RESPONSE;
import static com.osearch.crawler.fixture.PageDynamoDbRepositoryFixture.PAGE;
import static com.osearch.crawler.fixture.PageDynamoDbRepositoryFixture.PAGES_NUMBER;
import static com.osearch.crawler.fixture.PageDynamoDbRepositoryFixture.PAGE_RESPONSE;
import static com.osearch.crawler.fixture.PageDynamoDbRepositoryFixture.TABLE_NAME;
import static com.osearch.crawler.fixture.PageDynamoDbRepositoryFixture.URL_HASH;
import static com.osearch.crawler.fixture.PageDynamoDbRepositoryFixture.pageRequest;
import static com.osearch.crawler.fixture.PageDynamoDbRepositoryFixture.putPageRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.osearch.crawler.adapter.out.repository.mapper.PageDynamoDbMapper;
import com.osearch.crawler.adapter.out.repository.properties.DynamoDbRepositoryProperties;
import com.osearch.crawler.application.port.exception.DataAccessException;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

@Tag("category.UnitTest")
class PageDynamoDbRepositoryTest {

    @InjectMocks
    PageDynamoDbRepository target;

    @Mock
    DynamoDbClient client;

    @Mock
    PageDynamoDbMapper mapper;

    @Mock
    DynamoDbRepositoryProperties properties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(properties.getTableName()).thenReturn(TABLE_NAME);
        when(mapper.toPage(PAGE_RESPONSE)).thenReturn(Optional.of(PAGE));
        when(mapper.toRequest(PAGE, TABLE_NAME)).thenReturn(putPageRequest());
    }

    @Test
    void shouldFindByUrlHashWhenPageExists() {
        when(client.getItem(pageRequest(URL_HASH))).thenReturn(PAGE_RESPONSE);

        var saved = target.findByUrlHash(URL_HASH);
        assertEquals(PAGE, saved.get());
    }

    @Test
    void shouldNotFindByUrlHashWhenPageDoesntExist() {
        when(client.getItem(pageRequest(URL_HASH))).thenReturn(EMPTY_PAGE_RESPONSE);

        var saved = target.findByUrlHash(URL_HASH);
        assertTrue(saved.isEmpty());
    }

    @Test
    void shouldCountPages() {
        when(client.scan(COUNT_REQUEST)).thenReturn(COUNT_RESPONSE);

        var count = target.count();
        assertEquals(PAGES_NUMBER, count);
    }

    @Test
    void shouldSaveNewPages() {
        when(client.getItem(pageRequest(URL_HASH))).thenReturn(EMPTY_PAGE_RESPONSE);

        target.save(PAGE);
        verify(client, times(1)).putItem(any(PutItemRequest.class));
    }

    @Test
    void shouldThrowDataAccessExceptionIfErrorHappens() {
        when(client.getItem(pageRequest(URL_HASH))).thenThrow();

        assertThrows(DataAccessException.class,
            () -> target.findByUrlHash(PAGE.getUrlHash()));
    }
}