package com.osearch.crawler.service.rest;

import com.osearch.crawler.config.properties.RestProperties;
import com.osearch.crawler.service.rest.exception.RestForbiddenException;
import com.osearch.crawler.service.rest.exception.RestInvalidResponseException;
import com.osearch.crawler.service.rest.exception.RestServiceException;
import com.osearch.crawler.service.rest.exception.RestToManyRequestsException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@Log4j2
@RequiredArgsConstructor
public class RestServiceImpl implements RestService {
    private final RestTemplate restTemplate;
    private final RestProperties properties;

    @Override
    public String get(String url) {
        try {
            var response = httpGet(url, 0);
            validateResponse(response);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            var code = e.getStatusCode();
            if (code.equals(HttpStatus.FORBIDDEN)) {
                throw new RestForbiddenException(e.getMessage(), e);
            } else if (code.equals(HttpStatus.TOO_MANY_REQUESTS)) {
                throw new RestToManyRequestsException(e.getMessage(), e);
            } else {
                throw new RestServiceException(e.getMessage(), e);
            }
        } catch (RestServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new RestServiceException(e.getMessage(), e);
        }
    }

    private ResponseEntity<String> httpGet(String url, int redirectCount) {
        if (redirectCount >= properties.getMaxRedirects()) {
            throw new RestInvalidResponseException("Too many redirects: " + redirectCount);
        }

        var response = executeRequest(url);
        if (response.getStatusCode().is3xxRedirection()) {
            var redirect = response.getHeaders().getLocation();
            if (redirect != null && !redirect.toString().isEmpty()) {
                var redirectUrl = redirect.toString();
                log.debug("Redirecting to {}", redirectUrl);
                return httpGet(redirectUrl, redirectCount + 1);
            }
        }

        return response;
    }

    private ResponseEntity<String> executeRequest(String url) {
        log.debug("Sending HTTP GET for {}", url);
        return restTemplate.getForEntity(url, String.class);
    }

    private void validateResponse(ResponseEntity<String> response) {
        if (!isHtmlResponse(response)) {
            throw new RestInvalidResponseException("Not a HTML page");
        } else if (response.getBody() == null) {
            throw new RestInvalidResponseException("Null response body");
        }
    }

    private static boolean isHtmlResponse(ResponseEntity<String> response) {
        var headers = response.getHeaders();
        var contentType = headers.getContentType();
        return contentType != null && contentType.includes(MediaType.TEXT_HTML);
    }
}
