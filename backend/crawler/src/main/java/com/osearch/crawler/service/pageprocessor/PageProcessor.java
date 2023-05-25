package com.osearch.crawler.service.pageprocessor;

import com.osearch.crawler.service.entity.URL;

import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

/**
 * Used to assemble a URL object from string url.
 */
public interface PageProcessor {

    /**
     * Requests a page, reads its content and returns a URL object.
     *
     * @param url  url to read
     *
     * @return URL object
     *
     *  @throws ResourceAccessException  if response body is not HTML or body is empty
     *  @throws RestClientException      if any http request error happens
     */
    URL process(String url);
}
