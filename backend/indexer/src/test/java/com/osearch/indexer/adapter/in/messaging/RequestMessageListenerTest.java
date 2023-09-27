package com.osearch.indexer.adapter.in.messaging;

import static com.osearch.indexer.fixture.RequestMessageListenerFixture.INVALID_JSON;
import static com.osearch.indexer.fixture.RequestMessageListenerFixture.JSON;
import static com.osearch.indexer.fixture.RequestMessageListenerFixture.REQUEST;
import static com.osearch.indexer.fixture.RequestMessageListenerFixture.TOPIC;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.osearch.indexer.adapter.in.messaging.mapper.IndexRequestMapper;
import com.osearch.indexer.application.usecase.IndexerUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class RequestMessageListenerTest {

    @InjectMocks
    RequestMessageListener target;

    @Mock
    IndexRequestMapper mapper;

    @Mock
    IndexerUseCase useCase;

    @Mock
    InMessagingProperties properties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(properties.getTopic()).thenReturn(TOPIC);
        when(mapper.map(JSON)).thenReturn(REQUEST);
        when(mapper.map(INVALID_JSON)).thenThrow(IllegalArgumentException.class);
    }


    @Test
    void shouldStartIndexingWhenRequestIsValid() {
        target.listen(JSON);
        verify(useCase, times(1)).process(REQUEST);
    }

    @Test
    void shouldNotStartIndexingWhenRequestIsInvalid() {
        target.listen(INVALID_JSON);
        verify(useCase, times(0)).process(REQUEST);
    }
}