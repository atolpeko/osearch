package com.osearch.crawler.service.http;

import static com.osearch.crawler.fixture.HttpServiceFixture.HTML;
import static com.osearch.crawler.fixture.HttpServiceFixture.JSON;
import static com.osearch.crawler.fixture.HttpServiceFixture.MAX_REDIRECTS;
import static com.osearch.crawler.fixture.HttpServiceFixture.REDIRECT_URI;
import static com.osearch.crawler.fixture.HttpServiceFixture.REDIRECT_URL;
import static com.osearch.crawler.fixture.HttpServiceFixture.URL;

import static com.osearch.crawler.fixture.HttpServiceFixture.response;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

import com.osearch.crawler.config.properties.RestProperties;
import com.osearch.crawler.service.http.exception.HttpForbiddenException;
import com.osearch.crawler.service.http.exception.HttpInvalidResponseException;
import com.osearch.crawler.service.http.exception.HttpServiceException;
import com.osearch.crawler.service.http.exception.HttpToManyRequestsException;

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
class HttpServiceImplTest {

    @InjectMocks
    HttpServiceImpl target;

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
    void shouldReturnResponseWhenUrlReturnsHtml() {
        when(restTemplate.getForEntity(URL, String.class)).thenReturn(response);
        when(response.getStatusCode()).thenReturn(HttpStatus.OK);
        when(response.getBody()).thenReturn(HTML);
        when(headers.getContentType()).thenReturn(MediaType.TEXT_HTML);

        var response = target.get(URL);
        assertEquals(response(), response);
    }

    @Test
    void shouldReturnResponseWhenUrlReturnsRedirectThatReturnsHtml() {
        when(restTemplate.getForEntity(URL, String.class)).thenReturn(response);
        when(restTemplate.getForEntity(REDIRECT_URL, String.class)).thenReturn(response);
        when(response.getStatusCode())
                .thenReturn(HttpStatus.MULTIPLE_CHOICES)
                .thenReturn(HttpStatus.OK);
        when(response.getBody()).thenReturn(HTML);
        when(headers.getLocation()).thenReturn(REDIRECT_URI);
        when(headers.getContentType()).thenReturn(MediaType.TEXT_HTML);

        var response = target.get(URL);
        assertEquals(response(), response);
    }

    @Test
    void shouldThrowHttpInvalidResponseExceptionWhenResponseIsNotHtml() {
        when(restTemplate.getForEntity(URL, String.class)).thenReturn(response);
        when(response.getStatusCode()).thenReturn(HttpStatus.OK);
        when(response.getBody()).thenReturn(JSON);
        when(headers.getContentType()).thenReturn(MediaType.APPLICATION_JSON);

        assertThrows(HttpInvalidResponseException.class, () -> target.get(URL));
    }

    @Test
    void shouldThrowHttpInvalidResponseExceptionWhenBodyIsEmpty() {
        when(restTemplate.getForEntity(URL, String.class)).thenReturn(response);
        when(response.getStatusCode()).thenReturn(HttpStatus.OK);
        when(response.getBody()).thenReturn(null);
        when(headers.getContentType()).thenReturn(MediaType.TEXT_HTML);

        assertThrows(HttpInvalidResponseException.class, () -> target.get(URL));
    }

    @Test
    void shouldThrowHttpForbiddenExceptionWhenResponseIsForbidden() {
        when(restTemplate.getForEntity(URL, String.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN));

        assertThrows(HttpForbiddenException.class, () -> target.get(URL));
    }

    @Test
    void shouldThrowHttpToManyRequestsExceptionWhenResponseIsToManyRequest() {
        when(restTemplate.getForEntity(URL, String.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS));

        assertThrows(HttpToManyRequestsException.class, () -> target.get(URL));
    }

    @Test
    void shouldThrowHttpServiceExceptionWhenUrlReturnsToManyRedirects() {
        when(restTemplate.getForEntity(URL, String.class)).thenReturn(response);
        when(restTemplate.getForEntity(REDIRECT_URL, String.class)).thenReturn(response);
        when(response.getStatusCode()).thenReturn(HttpStatus.MULTIPLE_CHOICES);
        when(headers.getLocation()).thenReturn(REDIRECT_URI);

        assertThrows(HttpServiceException.class, () -> target.get(URL));
    }
}