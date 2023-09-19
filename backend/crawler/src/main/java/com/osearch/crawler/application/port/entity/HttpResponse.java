package com.osearch.crawler.application.port.entity;

import java.time.Duration;

/**
 * An interface representing an HTTP response.
 */
public interface HttpResponse {

    String getUrl();
    String getContent();
    Duration getLoadTime();
}
