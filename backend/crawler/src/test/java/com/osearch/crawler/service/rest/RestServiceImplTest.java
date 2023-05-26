package com.osearch.crawler.service.rest;

import static com.osearch.crawler.fixture.RestServiceFixture.HTML;
import static com.osearch.crawler.fixture.RestServiceFixture.JSON;
import static com.osearch.crawler.fixture.RestServiceFixture.MAX_REDIRECTS;
import static com.osearch.crawler.fixture.RestServiceFixture.REDIRECT_URI;
import static com.osearch.crawler.fixture.RestServiceFixture.REDIRECT_URL;
import static com.osearch.crawler.fixture.RestServiceFixture.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

import com.osearch.crawler.config.properties.RestProperties;
import com.osearch.crawler.service.rest.exception.RestForbiddenException;
import com.osearch.crawler.service.rest.exception.RestInvalidResponseException;
import com.osearch.crawler.service.rest.exception.RestServiceException;
import com.osearch.crawler.service.rest.exception.RestToManyRequestsException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Tag("category.UnitTest")
class RestServiceImplTest {

    @InjectMocks
    RestServiceImpl target;

    @Mock
    RestTemplate restTemplate;

    @Mock
    ResponseEntity<String> response;

    @Mock
    HttpHeaders headers;

    @Mock
    RestProperties properties;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(response.getHeaders()).thenReturn(headers);
        when(properties.getMaxRedirects()).thenReturn(MAX_REDIRECTS);
    }

    @Test
    void shouldGetDocumentWhenUrlReturnsHtml() {
        when(restTemplate.getForEntity(URL, String.class)).thenReturn(response);
        when(response.getStatusCode()).thenReturn(HttpStatus.OK);
        when(response.getBody()).thenReturn(HTML);
        when(headers.getContentType()).thenReturn(MediaType.TEXT_HTML);

        var page = target.get(URL);
        assertEquals(HTML, page);
    }

    @Test
    void shouldGetDocumentWhenUrlReturnsRedirectThatReturnsHtml() {
        when(restTemplate.getForEntity(URL, String.class)).thenReturn(response);
        when(restTemplate.getForEntity(REDIRECT_URL, String.class)).thenReturn(response);
        when(response.getStatusCode())
                .thenReturn(HttpStatus.MULTIPLE_CHOICES)
                .thenReturn(HttpStatus.OK);
        when(response.getBody()).thenReturn(HTML);
        when(headers.getLocation()).thenReturn(REDIRECT_URI);
        when(headers.getContentType()).thenReturn(MediaType.TEXT_HTML);

        var page = target.get(URL);
        assertEquals(HTML, page);
    }

    @Test
    void shouldThrowRestInvalidResponseExceptionWhenResponseIsNotHtml() {
        when(restTemplate.getForEntity(URL, String.class)).thenReturn(response);
        when(response.getStatusCode()).thenReturn(HttpStatus.OK);
        when(response.getBody()).thenReturn(JSON);
        when(headers.getContentType()).thenReturn(MediaType.APPLICATION_JSON);

        assertThrows(RestInvalidResponseException.class, () -> target.get(URL));
    }

    @Test
    void shouldThrowRestInvalidResponseExceptionWhenBodyIsEmpty() {
        when(restTemplate.getForEntity(URL, String.class)).thenReturn(response);
        when(response.getStatusCode()).thenReturn(HttpStatus.OK);
        when(response.getBody()).thenReturn(null);
        when(headers.getContentType()).thenReturn(MediaType.TEXT_HTML);

        assertThrows(RestInvalidResponseException.class, () -> target.get(URL));
    }

    @Test
    void shouldThrowRestForbiddenExceptionWhenResponseIsForbidden() {
        when(restTemplate.getForEntity(URL, String.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN));

        assertThrows(RestForbiddenException.class, () -> target.get(URL));
    }

    @Test
    void shouldThrowRestToManyRequestsExceptionWhenResponseIsToManyRequest() {
        when(restTemplate.getForEntity(URL, String.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS));

        assertThrows(RestToManyRequestsException.class, () -> target.get(URL));
    }

    @Test
    void shouldThrowRestServiceExceptionWhenUrlReturnsToManyRedirects() {
        when(restTemplate.getForEntity(URL, String.class)).thenReturn(response);
        when(restTemplate.getForEntity(REDIRECT_URL, String.class)).thenReturn(response);
        when(response.getStatusCode()).thenReturn(HttpStatus.MULTIPLE_CHOICES);
        when(headers.getLocation()).thenReturn(REDIRECT_URI);

        assertThrows(RestServiceException.class, () -> target.get(URL));
    }
}