package com.osearch.crawler.application.usecase;

import static com.osearch.crawler.fixture.CrawlerUseCaseFixture.INITIAL_URLS;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.osearch.crawler.application.port.HttpService;
import com.osearch.crawler.application.port.PageMessageSender;
import com.osearch.crawler.application.port.PageRepository;
import com.osearch.crawler.application.properties.ApplicationProperties;
import com.osearch.crawler.application.usecase.exception.CrawlerAlreadyRunningException;
import com.osearch.crawler.application.usecase.exception.CrawlerNotRunningException;
import com.osearch.crawler.application.usecase.exception.CrawlerServiceException;
import com.osearch.crawler.domain.service.executor.BackgroundExecutor;
import com.osearch.crawler.domain.service.htmlprocessor.HtmlProcessor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class CrawlerUseCaseImplTest {

    @InjectMocks
    CrawlerUseCaseImpl target;

    @Mock
    BackgroundExecutor executor;

    @Mock
    HttpService httpService;

    @Mock
    HtmlProcessor htmlProcessor;

    @Mock
    PageMessageSender messageSender;

    @Mock
    PageRepository pageRepository;

    @Mock
    ApplicationProperties properties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldStart() {
        doAnswer(i ->  when(target.isRunning()).thenReturn(true))
            .when(executor).execute(any());

        target.start(INITIAL_URLS);
        assertTrue(target.isRunning());
    }

    @Test
    void shouldStop() {
        doAnswer(i ->  when(target.isRunning()).thenReturn(true))
            .when(executor).execute(any());
        doAnswer(i ->  when(target.isRunning()).thenReturn(false))
            .when(executor).stop();

        target.start(INITIAL_URLS);
        target.stop();

        assertFalse(target.isRunning());
    }

    @Test
    void shouldThrowExceptionWhenCrawlerIsAlreadyRunning() {
        doAnswer(i ->  when(target.isRunning()).thenReturn(true))
            .when(executor).execute(any());

        target.start(INITIAL_URLS);
        assertThrows(CrawlerAlreadyRunningException.class,
            () -> target.start(INITIAL_URLS));
    }

    @Test
    void shouldThrowExceptionWhenCrawlerIsNotRunning() {
        assertThrows(CrawlerNotRunningException.class, () -> target.stop());
    }

    @Test
    void shouldThrowExceptionWhenErrorHappens() {
        doThrow(RuntimeException.class).when(executor).execute(any());
        assertThrows(CrawlerServiceException.class,
            () -> target.start(INITIAL_URLS));
    }
}