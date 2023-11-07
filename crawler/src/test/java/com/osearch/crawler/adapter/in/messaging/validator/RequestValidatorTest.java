package com.osearch.crawler.adapter.in.messaging.validator;

import static com.osearch.crawler.fixture.RequestValidatorFixture.INVALID_STOP_REQUEST;
import static com.osearch.crawler.fixture.RequestValidatorFixture.START_REQUEST_BLANK_URLS;
import static com.osearch.crawler.fixture.RequestValidatorFixture.START_REQUEST_INVALID_URLS;
import static com.osearch.crawler.fixture.RequestValidatorFixture.START_REQUEST_NO_URLS;
import static com.osearch.crawler.fixture.RequestValidatorFixture.VALID_START_REQUEST;
import static com.osearch.crawler.fixture.RequestValidatorFixture.VALID_STOP_REQUEST;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("category.UnitTest")
class RequestValidatorTest {
    private RequestValidator target;

    @BeforeEach
    void setUp() {
        target = new RequestValidator();
    }

    @Test
    void shouldReturnEmptySetWhenStartRequestIsValid() {
        var violations = target.validate(VALID_START_REQUEST);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldReturnEmptySetWhenStopRequestIsValid() {
        var violations = target.validate(VALID_STOP_REQUEST);
        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldReturnViolationsWhenStartRequestHasNoUrl() {
        var violations = target.validate(START_REQUEST_NO_URLS);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldReturnViolationsWhenStartRequestHasBlankUrl() {
        var violations = target.validate(START_REQUEST_BLANK_URLS);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldReturnViolationsWhenStartRequestHasInvalidUrl() {
        var violations = target.validate(START_REQUEST_INVALID_URLS);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldReturnViolationsWhenStopRequestIsInvalid() {
        var violations = target.validate(INVALID_STOP_REQUEST);
        assertFalse(violations.isEmpty());
    }
}