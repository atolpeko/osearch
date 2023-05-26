package com.osearch.crawler.service.rest;

import com.osearch.crawler.service.rest.exception.RestServiceException;
import com.osearch.crawler.service.rest.exception.RestForbiddenException;
import com.osearch.crawler.service.rest.exception.RestToManyRequestsException;
import com.osearch.crawler.service.rest.exception.RestInvalidResponseException;

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
     * @throws RestForbiddenException        if request was forbidden
     * @throws RestToManyRequestsException   if client have done to many requests
     * @throws RestInvalidResponseException  if response is not HTML or response body is empty
     * @throws RestServiceException          if any http request error happens
     */
    String get(String url);
}
