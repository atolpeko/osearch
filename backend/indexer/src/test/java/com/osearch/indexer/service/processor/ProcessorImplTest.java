package com.osearch.indexer.service.processor;

import static com.osearch.indexer.fixture.ProcessorFixture.NESTED_URL_1;
import static com.osearch.indexer.fixture.ProcessorFixture.NESTED_URL_2;
import static com.osearch.indexer.fixture.ProcessorFixture.SAVED_ID;
import static com.osearch.indexer.fixture.ProcessorFixture.URL;
import static com.osearch.indexer.fixture.ProcessorFixture.keywordDtos;
import static com.osearch.indexer.fixture.ProcessorFixture.nestedPage1;
import static com.osearch.indexer.fixture.ProcessorFixture.nestedPage1Dto;
import static com.osearch.indexer.fixture.ProcessorFixture.nestedPage2;
import static com.osearch.indexer.fixture.ProcessorFixture.nestedPage2Dto;
import static com.osearch.indexer.fixture.ProcessorFixture.page;
import static com.osearch.indexer.fixture.ProcessorFixture.pageDto;
import static com.osearch.indexer.fixture.ProcessorFixture.request;
import static com.osearch.indexer.fixture.ProcessorFixture.savedPageDto;

import static org.awaitility.Awaitility.await;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.osearch.indexer.inout.messaging.producer.IndexChangedMessageSender;
import com.osearch.indexer.inout.repository.KeywordRepository;
import com.osearch.indexer.inout.repository.PageRepository;
import com.osearch.indexer.inout.repository.mapper.KeywordMapper;
import com.osearch.indexer.inout.repository.mapper.PageMapper;
import com.osearch.indexer.inout.repository.transaction.TransactionExecutor;
import com.osearch.indexer.service.analyzer.ContentAnalyzer;
import com.osearch.indexer.service.entity.IndexRequest;
import com.osearch.indexer.service.executor.BackgroundExecutor;
import com.osearch.indexer.service.executor.BackgroundExecutorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import java.util.function.Supplier;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class ProcessorImplTest {
    private BackgroundExecutor executor;
    private BlockingDeque<IndexRequest> requests;

    @Mock
    ContentAnalyzer analyzer;

    @Mock
    IndexChangedMessageSender messageSender;

    @Mock
    TransactionExecutor transactionExecutor;

    @Mock
    PageRepository pageRepository;

    @Mock
    KeywordRepository keywordRepository;

    @Mock
    PageMapper pageMapper;

    @Mock
    KeywordMapper keywordMapper;

    @BeforeEach
    void setUp() {
        executor = new BackgroundExecutorImpl();
        requests = new LinkedBlockingDeque<>();
        MockitoAnnotations.initMocks(this);
    }

    @BeforeEach
    void setIpMocks() {
        when(analyzer.analyze(request())).thenReturn(page());

        when(transactionExecutor.inTransaction(any(Supplier.class)))
                .thenAnswer(invocation -> {
                    var supplier = (Supplier) invocation.getArguments()[0];
                    return supplier.get();
        });

        when(pageRepository.findByUrl(URL)).thenReturn(Optional.empty());
        when(pageRepository.findByUrl(NESTED_URL_1)).thenReturn(Optional.empty());
        when(pageRepository.findByUrl(NESTED_URL_2)).thenReturn(Optional.empty());
        when(pageRepository.save(any())).thenReturn(savedPageDto());

        when(keywordRepository.findByValue(any())).thenReturn(Optional.empty());
        when(keywordRepository.saveAll(any())).thenReturn(new ArrayList<>(keywordDtos()));

        when(pageMapper.toDto(page())).thenReturn(pageDto());
        when(pageMapper.toDto(nestedPage1())).thenReturn(nestedPage1Dto());
        when(pageMapper.toDto(nestedPage2())).thenReturn(nestedPage2Dto());
    }

    @AfterEach
    void tearDown() {
        if (executor.isRunning()) {
            executor.stop();
        }
    }

    @Test
    void shouldProcess() {
        requests.add(request());
        executor.execute(processor());
        await().pollDelay(7, TimeUnit.SECONDS).until(() -> true);

        verify(messageSender, times(1)).send(SAVED_ID);
    }

    private List<Processor> processor() {
        var processor = ProcessorImpl.builder()
                .id(0)
                .requests(requests)
                .analyzer(analyzer)
                .messageSender(messageSender)
                .pageRepository(pageRepository)
                .keywordRepository(keywordRepository)
                .pageMapper(pageMapper)
                .transactionExecutor(transactionExecutor)
                .keywordMapper(keywordMapper)
                .build();

        return List.of(processor);
    }
}