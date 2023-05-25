package com.osearch.crawler.service;

import com.osearch.crawler.service.exception.CrawlerAlreadyRunningException;
import com.osearch.crawler.service.exception.CrawlerServiceException;
import com.osearch.crawler.service.exception.CrawlerNotRunningException;

import java.util.List;

/**
 * Used to start / stop crawler.
 */
public interface CrawlerService {

    /**
     * Starts looking for new URLs starting from initialUrls.
     *
     * @param initialUrls  initial URLs to search for
     *
     * @throws CrawlerAlreadyRunningException  if crawler is already running
     * @throws CrawlerServiceException         if any other error happens
     */
    void start(List<String> initialUrls);

    /**
     * Stop crawler service.
     *
     * @throws CrawlerNotRunningException  if crawler service is not running
     */
    void stop();

    /**
     * @return true if crawler service is running, false otherwise
     */
    boolean isRunning();

    /**
     * @return the number of processed URLs
     */
    long urlsProcessed();
}
