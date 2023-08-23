package com.osearch.crawler.boot.config.rest;

import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class UserAgentInterceptor implements ClientHttpRequestInterceptor {
    private final String userAgent;

    @Override
    public ClientHttpResponse intercept(
        HttpRequest request,
        byte[] body,
        ClientHttpRequestExecution execution
    ) throws IOException {
        var headers = request.getHeaders();
        log.debug("Adding headers to request: USER_AGENT = {}", userAgent);
        headers.add(HttpHeaders.USER_AGENT, userAgent);

        return execution.execute(request, body);
    }
}

