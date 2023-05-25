package com.osearch.crawler.service.crawler;

import static com.osearch.crawler.fixture.CrawlerFixture.INITIAL_URL;
import static com.osearch.crawler.fixture.CrawlerFixture.NESTED_URL_1;
import static com.osearch.crawler.fixture.CrawlerFixture.NESTED_URL_2;
import static com.osearch.crawler.fixture.CrawlerFixture.NESTED_URL_3;
import static com.osearch.crawler.fixture.CrawlerFixture.RESULT_URLS_COUNT;
import static com.osearch.crawler.fixture.CrawlerFixture.getCrawlers;
import static com.osearch.crawler.fixture.CrawlerFixture.initialUrl;
import static com.osearch.crawler.fixture.CrawlerFixture.nestedUrl1;
import static com.osearch.crawler.fixture.CrawlerFixture.nestedUrl2;
import static com.osearch.crawler.fixture.CrawlerFixture.nestedUrl3;

import static org.awaitility.Awaitility.await;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import com.osearch.crawler.service.entity.URL;
import com.osearch.crawler.service.executor.BackgroundExecutor;
import com.osearch.crawler.service.executor.BackgroundExecutorImpl;
import com.osearch.crawler.service.pageprocessor.PageProcessor;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class CrawlerImplTest {
    private BackgroundExecutor executor;
    private BlockingDeque<URL> urls;

    @Mock
    PageProcessor pageProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(pageProcessor.process(INITIAL_URL)).thenReturn(initialUrl());
        when(pageProcessor.process(NESTED_URL_1)).thenReturn(nestedUrl1());
        when(pageProcessor.process(NESTED_URL_2)).thenReturn(nestedUrl2());
        when(pageProcessor.process(NESTED_URL_3)).thenReturn(nestedUrl3());

        executor = new BackgroundExecutorImpl();
        urls = new LinkedBlockingDeque<>();
    }

    @Test
    void shouldFindUrls() {
        var crawlers = getCrawlers(urls, INITIAL_URL, pageProcessor);
        executor.execute(crawlers);
        await().atMost(3, TimeUnit.SECONDS)
                .until(() -> !executor.isRunning());

        assertEquals(RESULT_URLS_COUNT, urls.size());
    }
}