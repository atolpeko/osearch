package com.osearch.crawler.service.rest;

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

/**
 * Used to request an url and get an HTML page content.
 */
public interface RestService {

    /**
     * Request an url and get HTML page content.
     *
     * @param url  url to request
     *
     * @return HTML page content
     *
     * @throws ResourceAccessException  if response is not HTML or response body is empty
     * @throws RestClientException      if any http request error happens
     */
    String get(String url);
}
