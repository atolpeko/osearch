package com.osearch.crawler.application.service;

import static com.osearch.crawler.fixture.CrawlerFixture.CONTENT;
import static com.osearch.crawler.fixture.CrawlerFixture.INITIAL_URL;
import static com.osearch.crawler.fixture.CrawlerFixture.NESTED_PAGE_1;
import static com.osearch.crawler.fixture.CrawlerFixture.NESTED_PAGE_2;
import static com.osearch.crawler.fixture.CrawlerFixture.NESTED_PAGE_3;
import static com.osearch.crawler.fixture.CrawlerFixture.NESTED_URL_1;
import static com.osearch.crawler.fixture.CrawlerFixture.NESTED_URL_2;
import static com.osearch.crawler.fixture.CrawlerFixture.NESTED_URL_3;
import static com.osearch.crawler.fixture.CrawlerFixture.PAGE;
import static com.osearch.crawler.fixture.CrawlerFixture.PAGE_RESPONSE;
import static com.osearch.crawler.fixture.CrawlerFixture.RESULT_URLS_COUNT;
import static com.osearch.crawler.fixture.CrawlerFixture.getCrawlers;

import static org.awaitility.Awaitility.await;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import com.osearch.crawler.application.port.HttpService;
import com.osearch.crawler.domain.entity.Page;
import com.osearch.crawler.domain.service.executor.BackgroundExecutor;
import com.osearch.crawler.domain.service.executor.BackgroundExecutorImpl;
import com.osearch.crawler.domain.service.hasher.Hasher;
import com.osearch.crawler.domain.service.htmlprocessor.HtmlProcessor;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class CrawlerTest {
    BackgroundExecutor executor;
    BlockingDeque<String> urlsToGet;
    BlockingDeque<Page> urlsToSave;

    @Mock
    HttpService httpService;

    @Mock
    HtmlProcessor htmlProcessor;

    @Mock
    Hasher hasher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(httpService.get(INITIAL_URL)).thenReturn(PAGE_RESPONSE);
        when(htmlProcessor.findNestedUrls(INITIAL_URL, CONTENT))
            .thenReturn(PAGE.getNestedUrls());

        when(httpService.get(NESTED_URL_1)).thenReturn(PAGE_RESPONSE);
        when(htmlProcessor.findNestedUrls(NESTED_URL_1, CONTENT))
            .thenReturn(NESTED_PAGE_1.getNestedUrls());

        when(httpService.get(NESTED_URL_2)).thenReturn(PAGE_RESPONSE);
        when(htmlProcessor.findNestedUrls(NESTED_URL_2, CONTENT))
            .thenReturn(NESTED_PAGE_2.getNestedUrls());

        when(httpService.get(NESTED_URL_3)).thenReturn(PAGE_RESPONSE);
        when(htmlProcessor.findNestedUrls(NESTED_URL_3, CONTENT))
            .thenReturn(NESTED_PAGE_3.getNestedUrls());

        executor = new BackgroundExecutorImpl();
        urlsToGet = new LinkedBlockingDeque<>();
        urlsToSave = new LinkedBlockingDeque<>();
    }

    @Test
    void shouldFindUrls() {
        urlsToGet.add(INITIAL_URL);

        var crawlers = getCrawlers(urlsToGet, urlsToSave,
            httpService, htmlProcessor, hasher);
        executor.execute(crawlers);
        await().pollDelay(8, TimeUnit.SECONDS).until(() -> true);

        assertEquals(RESULT_URLS_COUNT, urlsToSave.size());
    }
}