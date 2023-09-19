package com.osearch.crawler.application.usecase;

import com.osearch.crawler.application.usecase.exception.CrawlerAlreadyRunningException;
import com.osearch.crawler.application.usecase.exception.CrawlerNotRunningException;
import com.osearch.crawler.application.usecase.exception.CrawlerServiceException;

import java.util.List;

/**
 * The CrawlerUseCase interface defines the methods for interacting with crawler.
 */
public interface CrawlerUseCase {

    /**
     * Starts looking for new pages starting from initialUrls.
     *
     * @param initialUrls  initial URLs to search for
     *
     * @throws CrawlerAlreadyRunningException  if crawler is already running
     * @throws CrawlerServiceException         if any other error happens
     */
    void start(List<String> initialUrls);

    /**
     * Stop the crawler service.
     *
     * @throws CrawlerNotRunningException  if the crawler service is not running
     * @throws CrawlerServiceException     if any other error happens
     */
    void stop();

    /**
     * Checks if the crawler service is currently running.
     *
     * @return true if the crawler service is running, false otherwise.
     *
     * @throws CrawlerServiceException  if any error happens
     */
    boolean isRunning();
}
