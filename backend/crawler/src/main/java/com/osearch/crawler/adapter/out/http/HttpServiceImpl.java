package com.osearch.crawler.adapter.out.http;

import com.osearch.crawler.application.port.exception.HttpForbiddenException;
import com.osearch.crawler.application.port.exception.HttpInvalidResponseException;
import com.osearch.crawler.application.port.exception.HttpServiceException;
import com.osearch.crawler.application.port.exception.HttpToManyRequestsException;
import com.osearch.crawler.application.port.HttpService;
import com.osearch.crawler.application.port.entity.HttpResponse;

import java.time.Duration;
import java.time.Instant;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Log4j2
@RequiredArgsConstructor
public class HttpServiceImpl implements HttpService {
    private final RestTemplate restTemplate;
    private final HttpProperties properties;

    @Override
    public HttpResponse get(String url) {
        try {
            var start = Instant.now();
            var response = httpGet(url, 0);
            var loadTime = Duration.between(start, Instant.now());

            validateResponse(response);
            return new HttpResponseEntity(url, response.getBody(), loadTime);
        } catch (HttpClientErrorException e) {
            var code = e.getStatusCode();
            if (code.equals(HttpStatus.FORBIDDEN)) {
                throw new HttpForbiddenException(e.getMessage(), e);
            } else if (code.equals(HttpStatus.TOO_MANY_REQUESTS)) {
                throw new HttpToManyRequestsException(e.getMessage(), e);
            } else {
                throw new HttpServiceException(e.getMessage(), e);
            }
        } catch (HttpServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new HttpServiceException(e.getMessage(), e);
        }
    }

    private ResponseEntity<String> httpGet(String url, int redirectCount) {
        if (redirectCount >= properties.getMaxRedirects()) {
            throw new HttpInvalidResponseException("Too many redirects: " + redirectCount);
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
            throw new HttpInvalidResponseException("Not a HTML page");
        } else if (response.getBody() == null) {
            throw new HttpInvalidResponseException("Null response body");
        }
    }

    private static boolean isHtmlResponse(ResponseEntity<String> response) {
        var headers = response.getHeaders();
        var contentType = headers.getContentType();
        return contentType != null && contentType.includes(MediaType.TEXT_HTML);
    }
}
