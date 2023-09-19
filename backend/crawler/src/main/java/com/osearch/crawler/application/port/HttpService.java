package com.osearch.crawler.application.port;

import com.osearch.crawler.application.port.exception.HttpForbiddenException;
import com.osearch.crawler.application.port.exception.HttpInvalidResponseException;
import com.osearch.crawler.application.port.exception.HttpServiceException;
import com.osearch.crawler.application.port.exception.HttpToManyRequestsException;
import com.osearch.crawler.application.port.entity.HttpResponse;

/**
 * Represents an HTTP service that can send requests to a given URL and receive responses.
 */
public interface HttpService {

    /**
     * Request a URL and get Response object.
     *
     * @param url  URL to request
     *
     * @return Response object
     *
     * @throws HttpForbiddenException        if request was forbidden
     * @throws HttpToManyRequestsException   if client have done to many requests
     * @throws HttpInvalidResponseException  if response is not HTML or response body is empty
     * @throws HttpServiceException          if any HTTP request error happens
     */
    HttpResponse get(String url);
}
