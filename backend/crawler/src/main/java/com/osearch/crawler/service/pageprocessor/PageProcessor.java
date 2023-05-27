package com.osearch.crawler.service.pageprocessor;

import com.osearch.crawler.service.entity.URL;
import com.osearch.crawler.service.rest.exception.RestForbiddenException;
import com.osearch.crawler.service.rest.exception.RestInvalidResponseException;
import com.osearch.crawler.service.rest.exception.RestServiceException;
import com.osearch.crawler.service.rest.exception.RestToManyRequestsException;

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
     * @throws RestForbiddenException        if request was forbidden
     * @throws RestToManyRequestsException   if client have done to many requests
     * @throws RestInvalidResponseException  if response is not HTML or response body is empty
     * @throws RestServiceException          if any http request error happens
     */
    URL process(String url);
}
