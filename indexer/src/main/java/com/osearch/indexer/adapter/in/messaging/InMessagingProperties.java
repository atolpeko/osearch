package com.osearch.indexer.adapter.in.messaging;

/**
 * Input kafka messaging properties.
 */
public interface InMessagingProperties {

    /**
     * Returns the topic to listen to.
     *
     * @return The topic to listen to.
     */
    String getTopic();

    /**
     * Returns the group ID.
     *
     * @return The group ID.
     */
    String getGroupId();
}
