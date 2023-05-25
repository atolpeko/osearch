package com.osearch.crawler.service.crawler;

/**
 * New URLs searcher.
 */
public interface Crawler extends Runnable {

    /**
     * Starts an infinite loop searching for new URLs.
     */
    void run();
}
