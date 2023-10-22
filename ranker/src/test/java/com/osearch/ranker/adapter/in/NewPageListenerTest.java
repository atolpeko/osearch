package com.osearch.ranker.adapter.in;

import static com.osearch.ranker.fixture.NewPageListenerFixture.ID;
import static com.osearch.ranker.fixture.NewPageListenerFixture.INVALID_REQUEST_JSON;
import static com.osearch.ranker.fixture.NewPageListenerFixture.REQUEST;
import static com.osearch.ranker.fixture.NewPageListenerFixture.REQUEST_JSON;
import static com.osearch.ranker.fixture.NewPageListenerFixture.TOPIC;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.osearch.ranker.adapter.in.mapper.RequestMapper;
import com.osearch.ranker.adapter.in.properties.InMessagingProperties;
import com.osearch.ranker.application.usecase.RankerUseCase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class NewPageListenerTest {

    @InjectMocks
    NewPageListener target;

    @Mock
    RequestMapper mapper;

    @Mock
    RankerUseCase useCase;

    @Mock
    InMessagingProperties properties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(properties.getTopic()).thenReturn(TOPIC);
        when(mapper.toRequest(REQUEST_JSON)).thenReturn(REQUEST);
        when(mapper.toRequest(INVALID_REQUEST_JSON))
            .thenThrow(IllegalArgumentException.class);
    }

    @Test
    void shouldStartRankingWhenRequestIsValid() {
        target.listen(REQUEST_JSON);
        verify(useCase, times(1)).process(ID);
    }

    @Test
    void shouldNotStartRankingWhenRequestIsValid() {
        target.listen(INVALID_REQUEST_JSON);
        verify(useCase, times(0)).process(ID);
    }
}