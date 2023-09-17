package com.osearch.indexer.application;

import static com.osearch.indexer.fixture.IndexServiceFixture.ID;
import static com.osearch.indexer.fixture.IndexServiceFixture.page;
import static com.osearch.indexer.fixture.IndexServiceFixture.request;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.osearch.indexer.application.port.PageMessageSender;
import com.osearch.indexer.application.usecase.IndexerUseCaseImpl;
import com.osearch.indexer.application.port.PageRepository;
import com.osearch.indexer.domain.analyzer.ContentAnalyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class IndexerUseCaseImplTest {

    @InjectMocks
    IndexerUseCaseImpl target;

    @Mock
    ContentAnalyzer analyzer;

    @Mock
    PageRepository repository;

    @Mock
    PageMessageSender messageSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(analyzer.analyze(request())).thenReturn(page());
        when(repository.save(page())).thenReturn(ID);
    }

    @Test
    void shouldProcess() {
        target.process(request());
        verify(repository, times(1)).save(page());
    }

    @Test
    void shouldMessageWhenProcess() {
        target.process(request());
        verify(messageSender, times(1)).send(page());
    }
}