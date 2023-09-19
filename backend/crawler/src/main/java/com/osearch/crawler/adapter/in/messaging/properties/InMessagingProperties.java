package com.osearch.crawler.adapter.in.messaging.properties;

/**
 * Input messaging properties.
 */
public interface InMessagingProperties {

    /**
     * Retrieves the response topic.
     *
     * @return the response topic.
     */
    String getResponseTopic();

    /**
     * Retrieves the request topic to be used to listened to kafka requests.
     *
     * @return the request topic.
     */
    String getRequestTopic();

    /**
     * Returns the group ID to be used to listened to kafka requests.
     *
     * @return the group ID.
     */
    String getGroupId();
}
