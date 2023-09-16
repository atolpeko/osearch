package com.osearch.crawler.service.pageprocessor;

import com.osearch.crawler.service.entity.URL;
import com.osearch.crawler.service.http.exception.HttpForbiddenException;
import com.osearch.crawler.service.http.exception.HttpInvalidResponseException;
import com.osearch.crawler.service.http.exception.HttpServiceException;
import com.osearch.crawler.service.http.exception.HttpToManyRequestsException;

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
     * @throws HttpForbiddenException        if request was forbidden
     * @throws HttpToManyRequestsException   if client have done to many requests
     * @throws HttpInvalidResponseException  if response is not HTML or response body is empty
     * @throws HttpServiceException          if any http request error happens
     */
    URL process(String url);
}