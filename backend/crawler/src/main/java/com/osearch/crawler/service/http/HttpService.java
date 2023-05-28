package com.osearch.crawler.service.http;

import com.osearch.crawler.service.http.entity.Response;
import com.osearch.crawler.service.http.exception.HttpServiceException;
import com.osearch.crawler.service.http.exception.HttpForbiddenException;
import com.osearch.crawler.service.http.exception.HttpToManyRequestsException;
import com.osearch.crawler.service.http.exception.HttpInvalidResponseException;

/**
 * Used to request an url and get Response object.
 */
public interface HttpService {

    /**
     * Request an url and get Response object.
     *
     * @param url  url to request
     *
     * @return Response object
     *
     * @throws HttpForbiddenException        if request was forbidden
     * @throws HttpToManyRequestsException   if client have done to many requests
     * @throws HttpInvalidResponseException  if response is not HTML or response body is empty
     * @throws HttpServiceException          if any http request error happens
     */
    Response get(String url);
}
