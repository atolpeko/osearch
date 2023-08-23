package com.osearch.crawler.adapter.in.messaging.mapper;

import static com.osearch.crawler.fixture.RequestMapperFixture.invalidRequestJson;
import static com.osearch.crawler.fixture.RequestMapperFixture.invalidStartRequest;
import static com.osearch.crawler.fixture.RequestMapperFixture.invalidStartRequestJson;
import static com.osearch.crawler.fixture.RequestMapperFixture.invalidStopRequest;
import static com.osearch.crawler.fixture.RequestMapperFixture.startRequest;
import static com.osearch.crawler.fixture.RequestMapperFixture.startRequestJson;
import static com.osearch.crawler.fixture.RequestMapperFixture.stopRequest;
import static com.osearch.crawler.fixture.RequestMapperFixture.stopRequestJson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.crawler.adapter.in.messaging.entity.Request;
import com.osearch.crawler.adapter.in.messaging.validator.RequestValidator;

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
        when(mapper.readValue(startRequestJson(), Request.class)).thenReturn(startRequest());
        when(mapper.readValue(stopRequestJson(), Request.class)).thenReturn(stopRequest());
        when(validator.isValid(startRequest())).thenReturn(true);
        when(validator.isValid(stopRequest())).thenReturn(true);
        when(validator.isValid(invalidStartRequest())).thenReturn(false);
        when(validator.isValid(invalidStopRequest())).thenReturn(false);
    }

    @Test
    void shouldMapStartRequestWhenItsValid() throws JsonProcessingException {
        var result = target.toRequest(startRequestJson());
        assertEquals(startRequest(), result);
    }

    @Test
    void shouldMapStopRequestWhenItsValid() throws JsonProcessingException {
        var result = target.toRequest(stopRequestJson());
        assertEquals(stopRequest(), result);
    }

    @Test
    void shouldThrowExceptionWhenStartRequestIsInValid() {
        assertThrows(IllegalArgumentException.class,
            () -> target.toRequest(invalidStartRequestJson()));
    }

    @Test
    void shouldThrowExceptionWhenStopRequestIsInValid() {
        assertThrows(IllegalArgumentException.class,
            () -> target.toRequest(invalidRequestJson()));
    }
}