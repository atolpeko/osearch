package com.osearch.crawler.adapter.out.messaging.properties;

/**
 * Output messaging properties.
 */
public interface OutMessagingProperties {

    /**
     * Retrieves the topic to be used to send pages to kafka.
     *
     * @return the topic
     */
    String getTopic();
}
