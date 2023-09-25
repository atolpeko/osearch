package com.osearch.ranker.adapter.in.mapper;

import static com.osearch.ranker.fixture.RequestMapperFixture.INVALID_REQUEST_JSON;
import static com.osearch.ranker.fixture.RequestMapperFixture.REQUEST;
import static com.osearch.ranker.fixture.RequestMapperFixture.REQUEST_JSON;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.ranker.adapter.in.entity.Request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class RequestMapperTest {

    @InjectMocks
    RequestMapper target;

    @Mock
    ObjectMapper mapper;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        when(mapper.readValue(REQUEST_JSON, Request.class)).thenReturn(REQUEST);
        when(mapper.readValue(INVALID_REQUEST_JSON, Request.class))
            .thenThrow(JsonProcessingException.class);
    }

    @Test
    void shouldMapRequestWhenItsValid() {
        var result = target.toRequest(REQUEST_JSON);
        Assertions.assertEquals(REQUEST, result);
    }

    @Test
    void shouldThrowExceptionWhenRequestIsInValid() {
        assertThrows(IllegalArgumentException.class,
            () -> target.toRequest(INVALID_REQUEST_JSON));
    }
}