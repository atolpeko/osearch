package com.osearch.crawler.service.processor;

/**
 * New URLs processor..
 */
public interface Processor extends Runnable {

    /**
     * Starts an infinite loop processing new URLs.
     */
    void run();
}
