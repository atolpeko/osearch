package com.osearch.indexer.adapter.in.messaging.mapper;

import static com.osearch.indexer.fixture.IndexRequestMapperFixture.INVALID_JSON;
import static com.osearch.indexer.fixture.IndexRequestMapperFixture.JSON;
import static com.osearch.indexer.fixture.IndexRequestMapperFixture.REQUEST;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.indexer.domain.entity.IndexRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class IndexRequestMapperTest {

    @InjectMocks
    IndexRequestMapper target;

    @Mock
    ObjectMapper mapper;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        when(mapper.readValue(JSON, IndexRequest.class)).thenReturn(REQUEST);
        when(mapper.readValue(INVALID_JSON, IndexRequest.class))
            .thenThrow(JsonProcessingException.class);
    }

    @Test
    void shouldMapRequest() {
        var result = target.map(JSON);
        assertEquals(REQUEST, result);
    }

    @Test
    void shouldThrowExceptionWhenJsonIsInValid() {
        assertThrows(IllegalArgumentException.class,
            () -> target.map(INVALID_JSON));
    }
}