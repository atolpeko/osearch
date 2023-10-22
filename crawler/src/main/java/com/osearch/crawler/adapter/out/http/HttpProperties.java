package com.osearch.crawler.adapter.out.http;

/**
 * HTTP service properties.
 */
public interface HttpProperties {

    /**
     * Get the maximum number of redirects allowed.
     *
     * @return The maximum number of redirects allowed.
     */
    int getMaxRedirects();
}
