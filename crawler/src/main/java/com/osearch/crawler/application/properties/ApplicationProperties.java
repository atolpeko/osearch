package com.osearch.crawler.application.properties;

/**
 * Application properties.
 */
public interface ApplicationProperties {

    /**
     * Returns the number of crawler threads that should be used.
     *
     * @return the number of crawler threads.
     */
    int getCrawlerThreadsCount();

    /**
     * Returns the number of processor threads that should be used.
     *
     * @return the number of processor threads.
     */
    int getProcessorThreadsCount();

    /**
     * Returns the number of receptively found nested pages
     * to keep for later processing when crawling.
     *
     * @return the number of pages to keep.
     */
    int getPagesToKeepCount();
}
