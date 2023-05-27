package com.osearch.crawler.service.pageprocessor;

import static com.osearch.crawler.fixture.PageProcessorFixture.PAGE_HASH;
import static com.osearch.crawler.fixture.PageProcessorFixture.URL_HASH;
import static com.osearch.crawler.fixture.PageProcessorFixture.PAGE;
import static com.osearch.crawler.fixture.PageProcessorFixture.URL_STRING;
import static com.osearch.crawler.fixture.PageProcessorFixture.url;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

import com.osearch.crawler.service.hasher.Hasher;
import com.osearch.crawler.service.rest.RestService;
import com.osearch.crawler.service.rest.exception.RestServiceException;

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
    RestService restService;
    
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
        when(restService.get(URL_STRING)).thenReturn(PAGE);

        var url = target.process(URL_STRING);
        assertEquals(url(), url);
    }

    @Test
    void shouldThrowRestServiceExceptionWhenRestServiceFails() {;
        when(restService.get(URL_STRING)).thenThrow(RestServiceException.class);
        assertThrows(RestServiceException.class, () -> target.process(URL_STRING));
    }
}