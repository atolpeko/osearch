package com.osearch.indexer.adapter.out.messaging.properties;

/**
 * Output kafka messaging properties.
 */
public interface OutMessagingProperties {

    /**
     * Returns the topic to send messages to.
     *
     * @return the topic.
     */
    String getOutTopic();
}
