package com.osearch.crawler.application.properties;

/**
 * Application properties.
 */
public interface ApplicationProperties {

    int getCrawlerThreadsCount();
    int getProcessorThreadsCount();
    int getPagesToKeepCount();
}
