package com.osearch.crawler.service.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Component
@Log4j2
@RequiredArgsConstructor
public class RestServiceImpl implements RestService {
    private final RestTemplate restTemplate;

    @Override
    public String get(String url) {
        var response = httpGet(url);
        validateResponse(response);
        return response.getBody();
    }

    private ResponseEntity<String> httpGet(String url) {
        log.debug("Sending HTTP GET for {}", url);
        return restTemplate.getForEntity(url, String.class);
    }

    private void validateResponse(ResponseEntity<String> response) {
        if (!isHtmlResponse(response)) {
            throw new ResourceAccessException("Not a HTML page");
        } else if (response.getBody() == null) {
            throw new ResourceAccessException("Null response body");
        }
    }

    private static boolean isHtmlResponse(ResponseEntity<String> response) {
        var headers = response.getHeaders();
        var contentType = headers.getContentType();
        return contentType != null && contentType.includes(MediaType.TEXT_HTML);
    }
}
