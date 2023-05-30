package com.osearch.crawler.service.pageprocessor;

import static com.osearch.crawler.fixture.PageProcessorFixture.PAGE_HASH;
import static com.osearch.crawler.fixture.PageProcessorFixture.URL_HASH;
import static com.osearch.crawler.fixture.PageProcessorFixture.PAGE;
import static com.osearch.crawler.fixture.PageProcessorFixture.URL_STRING;
import static com.osearch.crawler.fixture.PageProcessorFixture.response;
import static com.osearch.crawler.fixture.PageProcessorFixture.url;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

import com.osearch.crawler.service.hasher.Hasher;
import com.osearch.crawler.service.http.HttpService;
import com.osearch.crawler.service.http.exception.HttpServiceException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Tag("category.UnitTest")
class PageProcessorImplTest {
    
    @InjectMocks
    PageProcessorImpl target;

    @Mock
    HttpService httpService;
    
    @Mock
    Hasher hasher;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(hasher.hash(URL_STRING)).thenReturn(URL_HASH);
        when(hasher.hash(PAGE)).thenReturn(PAGE_HASH);
    }

    @Test
    void shouldAssembleUrl() {
        when(httpService.get(URL_STRING)).thenReturn(response());

        var url = target.process(URL_STRING);
        assertEquals(url(), url);
    }

    @Test
    void shouldThrowRestServiceExceptionWhenRestServiceFails() {;
        when(httpService.get(URL_STRING)).thenThrow(HttpServiceException.class);
        assertThrows(HttpServiceException.class, () -> target.process(URL_STRING));
    }
}