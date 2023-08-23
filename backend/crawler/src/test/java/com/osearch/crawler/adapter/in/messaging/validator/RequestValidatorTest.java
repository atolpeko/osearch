package com.osearch.crawler.adapter.in.messaging.validator;

import static com.osearch.crawler.fixture.RequestValidatorFixture.invalidStartRequest;
import static com.osearch.crawler.fixture.RequestValidatorFixture.invalidStopRequest;
import static com.osearch.crawler.fixture.RequestValidatorFixture.validStartRequest;
import static com.osearch.crawler.fixture.RequestValidatorFixture.validStopRequest;

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
    void shouldReturnTrueWhenStartRequestIsValid() {
        var valid = target.isValid(validStartRequest());
        assertTrue(valid);
    }

    @Test
    void shouldReturnTrueWhenStopRequestIsValid() {
        var valid = target.isValid(validStopRequest());
        assertTrue(valid);
    }

    @Test
    void shouldReturnFalseWhenStartRequestIsInvalid() {
        var invalid = target.isValid(invalidStartRequest());
        assertFalse(invalid);
    }

    @Test
    void shouldReturnFalseWhenStopRequestIsInvalid() {
        var invalid = target.isValid(invalidStopRequest());
        assertFalse(invalid);
    }
}