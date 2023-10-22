package com.osearch.crawler.application.usecase;

import com.osearch.crawler.application.usecase.exception.CrawlerAlreadyRunningException;
import com.osearch.crawler.application.usecase.exception.CrawlerNotRunningException;
import com.osearch.crawler.application.usecase.exception.CrawlerUseCaseException;

import java.util.List;

/**
 * The CrawlerUseCase interface defines the methods for interacting with the crawler.
 */
public interface CrawlerUseCase {

    /**
     * Starts looking for new pages starting from initialUrls.
     *
     * @param initialUrls  initial URLs to search for
     *
     * @throws CrawlerAlreadyRunningException  if the crawler is already running
     * @throws CrawlerUseCaseException         if any other error happens
     */
    void start(List<String> initialUrls);

    /**
     * Stop the crawler.
     *
     * @throws CrawlerNotRunningException  if the crawler is not running
     * @throws CrawlerUseCaseException     if any other error happens
     */
    void stop();

    /**
     * Checks if the crawler is currently running.
     *
     * @return true if the crawler is running, false otherwise.
     *
     * @throws CrawlerUseCaseException  if any error happens
     */
    boolean isRunning();
}
