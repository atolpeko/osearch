package com.osearch.crawler.application.service;

import static com.osearch.crawler.fixture.ProcessorFixture.CHANGED_PAGE;
import static com.osearch.crawler.fixture.ProcessorFixture.PAGE_1;
import static com.osearch.crawler.fixture.ProcessorFixture.PAGE_2;
import static com.osearch.crawler.fixture.ProcessorFixture.URL_1_HASH;
import static com.osearch.crawler.fixture.ProcessorFixture.URL_2_HASH;
import static com.osearch.crawler.fixture.ProcessorFixture.getProcessors;

import static org.awaitility.Awaitility.await;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.osearch.crawler.application.port.PageMessageSender;
import com.osearch.crawler.application.port.PageRepository;
import com.osearch.crawler.domain.entity.Page;
import com.osearch.crawler.domain.service.executor.BackgroundExecutor;
import com.osearch.crawler.domain.service.executor.BackgroundExecutorImpl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class ProcessorTest {
    BackgroundExecutor executor;
    List<Processor> processors;
    BlockingDeque<Page> pages;

    @Mock
    PageMessageSender messageSender;

    @Mock
    PageRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        executor = new BackgroundExecutorImpl();
        pages = new LinkedBlockingDeque<>();
        processors = getProcessors(messageSender, repository, pages);
    }

    @Test
    void shouldMessageUrlsWhenTheyAreNotProcessed() {
        when(repository.findByUrlHash(URL_1_HASH))
            .thenReturn(Optional.empty())
            .thenReturn(Optional.of(PAGE_1));
        when(repository.findByUrlHash(URL_2_HASH))
            .thenReturn(Optional.empty())
            .thenReturn(Optional.of(PAGE_2));

        pages.addAll(List.of(PAGE_1, PAGE_2, PAGE_1, PAGE_2));
        executor.execute(processors);
        await().pollDelay(8, TimeUnit.SECONDS).until(() -> true);

        verify(messageSender, times(2)).send(any());
    }

    @Test
    void shouldMessageUrlsWhenPageChanged() {
        when(repository.findByUrlHash(URL_1_HASH))
            .thenReturn(Optional.of(PAGE_1));
        when(repository.findByUrlHash(URL_2_HASH))
            .thenReturn(Optional.of(PAGE_2));

        pages.addAll(List.of(PAGE_1, PAGE_2, CHANGED_PAGE, PAGE_2));
        executor.execute(processors);
        await().pollDelay(8, TimeUnit.SECONDS).until(() -> true);

        verify(messageSender, times(1)).send(any());
    }

    @Test
    void shouldNotMessageUrlsWhenTheyAreProcessed() {
        when(repository.findByUrlHash(URL_1_HASH))
            .thenReturn(Optional.of(PAGE_1));
        when(repository.findByUrlHash(URL_2_HASH))
            .thenReturn(Optional.of(PAGE_2));

        pages.addAll(List.of(PAGE_1, PAGE_2, PAGE_1, PAGE_2));
        executor.execute(processors);
        await().pollDelay(8, TimeUnit.SECONDS).until(() -> true);

        verify(messageSender, times(0)).send(any());
    }
}