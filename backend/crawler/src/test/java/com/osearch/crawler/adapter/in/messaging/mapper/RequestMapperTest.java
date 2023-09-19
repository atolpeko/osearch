package com.osearch.crawler.adapter.in.messaging.mapper;

import static com.osearch.crawler.fixture.RequestMapperFixture.INVALID_STOP_REQUEST_JSON;
import static com.osearch.crawler.fixture.RequestMapperFixture.INVALID_START_REQUEST;
import static com.osearch.crawler.fixture.RequestMapperFixture.INVALID_STOP_REQUEST;
import static com.osearch.crawler.fixture.RequestMapperFixture.INVALID_START_REQUEST_JSON;
import static com.osearch.crawler.fixture.RequestMapperFixture.START_REQUEST;
import static com.osearch.crawler.fixture.RequestMapperFixture.STOP_REQUEST;
import static com.osearch.crawler.fixture.RequestMapperFixture.STOP_REQUEST_JSON;
import static com.osearch.crawler.fixture.RequestMapperFixture.startRequestJson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.crawler.adapter.in.messaging.entity.Request;
import com.osearch.crawler.adapter.in.messaging.validator.RequestValidator;

import java.util.Collections;
import java.util.Set;

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

    @Mock
    RequestValidator validator;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);

        when(mapper.readValue(startRequestJson(), Request.class)).thenReturn(START_REQUEST);
        when(mapper.readValue(STOP_REQUEST_JSON, Request.class)).thenReturn(STOP_REQUEST);
        when(mapper.readValue(INVALID_STOP_REQUEST_JSON, Request.class))
            .thenThrow(JsonProcessingException.class);
        when(mapper.readValue(INVALID_START_REQUEST_JSON, Request.class))
            .thenThrow(JsonProcessingException.class);

        when(validator.validate(START_REQUEST)).thenReturn(Collections.emptySet());
        when(validator.validate(STOP_REQUEST)).thenReturn(Collections.emptySet());
        when(validator.validate(INVALID_START_REQUEST)).thenReturn(Set.of(""));
        when(validator.validate(INVALID_STOP_REQUEST)).thenReturn(Set.of(""));
    }

    @Test
    void shouldMapStartRequestWhenItsValid() {
        var result = target.toRequest(startRequestJson());
        assertEquals(START_REQUEST, result);
    }

    @Test
    void shouldMapStopRequestWhenItsValid() {
        var result = target.toRequest(STOP_REQUEST_JSON);
        assertEquals(STOP_REQUEST, result);
    }

    @Test
    void shouldThrowExceptionWhenStartRequestIsInValid() {
        assertThrows(IllegalArgumentException.class,
            () -> target.toRequest(INVALID_START_REQUEST_JSON));
    }

    @Test
    void shouldThrowExceptionWhenStopRequestIsInValid() {
        assertThrows(IllegalArgumentException.class,
            () -> target.toRequest(INVALID_STOP_REQUEST_JSON));
    }
}