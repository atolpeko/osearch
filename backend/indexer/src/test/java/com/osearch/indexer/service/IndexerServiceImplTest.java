package com.osearch.indexer.service;

import static com.osearch.indexer.fixture.IndexServiceFixture.ID;
import static com.osearch.indexer.fixture.IndexServiceFixture.page;
import static com.osearch.indexer.fixture.IndexServiceFixture.request;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.osearch.indexer.inout.messaging.producer.IndexChangedMessageSender;
import com.osearch.indexer.inout.repository.PageRepository;
import com.osearch.indexer.service.analyzer.ContentAnalyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class IndexerServiceImplTest {

    @InjectMocks
    IndexerServiceImpl target;

    @Mock
    ContentAnalyzer analyzer;

    @Mock
    IndexChangedMessageSender messageSender;

    @Mock
    PageRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(analyzer.analyze(request())).thenReturn(page());
        when(repository.save(page())).thenReturn(ID);
    }

    @Test
    void shouldProcess() {
        target.process(request());
        verify(messageSender, times(1)).send(ID);
    }
}