package com.osearch.indexer.application;

import static com.osearch.indexer.fixture.IndexUseCaseFixture.ID;
import static com.osearch.indexer.fixture.IndexUseCaseFixture.PAGE;
import static com.osearch.indexer.fixture.IndexUseCaseFixture.PAGES_COUNT;
import static com.osearch.indexer.fixture.IndexUseCaseFixture.REQUEST;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.osearch.indexer.application.port.PageMessageSender;
import com.osearch.indexer.application.usecase.IndexerUseCaseImpl;
import com.osearch.indexer.application.port.PageRepository;
import com.osearch.indexer.domain.IndexService;

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
    IndexService indexService;

    @Mock
    PageRepository repository;

    @Mock
    PageMessageSender messageSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(indexService.index(REQUEST)).thenReturn(PAGE);
        when(repository.save(PAGE)).thenReturn(ID);
        when(repository.countIndexed()).thenReturn(PAGES_COUNT);
    }

    @Test
    void shouldProcess() {
        target.process(REQUEST);
        verify(repository, times(1)).save(PAGE);
    }

    @Test
    void shouldMessageWhenProcess() {
        target.process(REQUEST);
        verify(messageSender, times(1)).send(PAGE);
    }

    @Test
    void shouldReturnCount() {
        var count = target.countIndexed();
        assertEquals(PAGES_COUNT, count);
    }
}