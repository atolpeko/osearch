package com.osearch.crawler.domain.service.htmlprocessor;

import static com.osearch.crawler.fixture.HtmlProcessorFixture.CONTENT;
import static com.osearch.crawler.fixture.HtmlProcessorFixture.INVALID_URL_REGEX;
import static com.osearch.crawler.fixture.HtmlProcessorFixture.URL_REGEX;
import static com.osearch.crawler.fixture.HtmlProcessorFixture.URL_STRING;
import static com.osearch.crawler.fixture.HtmlProcessorFixture.PAGE;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("category.UnitTest")
class HtmlProcessorTest {
    HtmlProcessorImpl target;

    @BeforeEach
    void setUp() {
        target = new HtmlProcessorImpl(URL_REGEX, INVALID_URL_REGEX);
    }

    @Test
    void shouldFindNestedUrls() {
        var urls = target.findNestedUrls(URL_STRING, CONTENT);
        assertEquals(PAGE.getNestedUrls(), urls);
    }
}