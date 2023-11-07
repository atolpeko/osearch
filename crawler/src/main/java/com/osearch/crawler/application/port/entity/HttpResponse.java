package com.osearch.crawler.application.port.entity;

import java.time.Duration;

/**
 * An interface representing an HTTP response.
 */
public interface HttpResponse {

    /**
     * Returns the requested URL.
     *
     * @return a string representing the requested URL.
     */
    String getUrl();

    /**
     * Returns the content of the requested HTML page.
     *
     * @return a string representing the content of the requested HTML page.
     */
    String getContent();

    /**
     * Returns the duration it takes to load the requested HTML page.
     *
     * @return the duration representing the time it takes to load the requested HTML page.
     */
    Duration getLoadTime();
}
