package com.osearch.crawler.service.rest;

import static com.osearch.crawler.fixture.RestServiceFixture.HTML;
import static com.osearch.crawler.fixture.RestServiceFixture.JSON;
import static com.osearch.crawler.fixture.RestServiceFixture.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        when(response.getHeaders()).thenReturn(headers);
    }

    @Test
    void shouldGetDocument() {
        when(restTemplate.getForEntity(URL, String.class)).thenReturn(response);
        when(response.getBody()).thenReturn(HTML);
        when(headers.getContentType()).thenReturn(MediaType.TEXT_HTML);

        var page = target.get(URL);
        assertEquals(HTML, page);
    }

    @Test
    void shouldThrowResourceAccessExceptionWhenNoHtml() {
        when(restTemplate.getForEntity(URL, String.class)).thenReturn(response);
        when(response.getBody()).thenReturn(JSON);
        when(response.getHeaders().getContentType())
                .thenReturn(MediaType.APPLICATION_JSON);

        assertThrows(ResourceAccessException.class, () -> target.get(URL));
    }

    @Test
    void shouldThrowResourceAccessExceptionWhenBodyIsEmpty() {;
        when(restTemplate.getForEntity(URL, String.class)).thenReturn(response);
        when(response.getBody()).thenReturn(null);
        when(response.getHeaders().getContentType())
                .thenReturn(MediaType.TEXT_HTML);

        assertThrows(ResourceAccessException.class, () -> target.get(URL));
    }
}