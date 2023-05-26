package com.osearch.crawler.inout.web.util;

import lombok.extern.log4j.Log4j2;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Log4j2
public class OutRequestLoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution
    ) throws IOException {
        logRequest(request, body);
        return execution.execute(request, body);
    }

    private void logRequest(HttpRequest request, byte[] body) {
        var message = "Request info: Method: {}, URL: {}, Headers: {}";
        if (body.length == 0) {
            log.debug(message, request.getMethod(),
                    request.getURI(), request.getHeaders());
        } else {
            message += ", Request Body: {}";
            var requestBody = new String(body, StandardCharsets.UTF_8);
            log.debug(message, request.getMethod(), request.getURI(),
                    request.getHeaders(), requestBody);
        }
    }
}
