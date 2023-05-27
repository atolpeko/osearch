package com.osearch.crawler.service.processor;

import static com.osearch.crawler.fixture.ProcessorFixture.URL_1_HASH;
import static com.osearch.crawler.fixture.ProcessorFixture.URL_2_HASH;
import static com.osearch.crawler.fixture.ProcessorFixture.changedUrl1;
import static com.osearch.crawler.fixture.ProcessorFixture.getProcessors;
import static com.osearch.crawler.fixture.ProcessorFixture.url1;
import static com.osearch.crawler.fixture.ProcessorFixture.url2;
import static com.osearch.crawler.fixture.ProcessorFixture.urlDto1;
import static com.osearch.crawler.fixture.ProcessorFixture.urlDto2;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.osearch.crawler.inout.messaging.mapper.MessageURLMapper;
import com.osearch.crawler.inout.messaging.producer.URLMessageSender;
import com.osearch.crawler.inout.repository.URLRepository;
import com.osearch.crawler.inout.repository.mapper.RepositoryURLMapper;
import com.osearch.crawler.service.entity.URL;
import com.osearch.crawler.service.executor.BackgroundExecutor;
import com.osearch.crawler.service.executor.BackgroundExecutorImpl;

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
class ProcessorImplTest {
    private BackgroundExecutor executor;
    private List<Runnable> processors;
    private BlockingDeque<URL> urls;

    @Mock
    URLMessageSender messageSender;

    @Mock
    URLRepository repository;

    @Mock
    MessageURLMapper messageURLMapper;

    @Mock
    RepositoryURLMapper repositoryURLMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        executor = new BackgroundExecutorImpl();
        urls = new LinkedBlockingDeque<>();
        processors = getProcessors(messageSender, repository,
                repositoryURLMapper, messageURLMapper, urls);
    }

    @Test
    void shouldMessageUrlsWhenTheyAreNotProcessed() {
        when(repository.findByUrlHash(URL_1_HASH))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(urlDto1()));
        when(repository.findByUrlHash(URL_2_HASH))
                .thenReturn(Optional.empty())
                .thenReturn(Optional.of(urlDto2()));

        urls.addAll(List.of(url1(), url2(), url1(), url2()));
        executor.execute(processors);
        await().pollDelay(8, TimeUnit.SECONDS).until(() -> true);

        verify(messageSender, times(2)).send(any());
    }

    @Test
    void shouldMessageUrlsWhenPageChanged() {
        when(repository.findByUrlHash(URL_1_HASH))
                .thenReturn(Optional.of(urlDto1()));
        when(repository.findByUrlHash(URL_2_HASH))
                .thenReturn(Optional.of(urlDto2()));

        urls.addAll(List.of(url1(), url2(), changedUrl1(), url2()));
        executor.execute(processors);
        await().pollDelay(8, TimeUnit.SECONDS).until(() -> true);

        verify(messageSender, times(1)).send(any());
    }

    @Test
    void shouldNotMessageUrlsWhenTheyAreProcessed() {
        when(repository.findByUrlHash(URL_1_HASH))
                .thenReturn(Optional.of(urlDto1()));
        when(repository.findByUrlHash(URL_2_HASH))
                .thenReturn(Optional.of(urlDto2()));

        urls.addAll(List.of(url1(), url2(), url1(), url2()));
        executor.execute(processors);
        await().pollDelay(8, TimeUnit.SECONDS).until(() -> true);

        verify(messageSender, times(0)).send(any());
    }
}