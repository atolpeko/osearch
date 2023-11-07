package com.osearch.ranker.adapter.in.properties;

/**
 * Input kafka messaging properties.
 */
public interface InMessagingProperties {

    /**
     * Returns the topic to listen to.
     *
     * @return the topic to listen to.
     */
    String getTopic();

    /**
     * Returns the group ID.
     *
     * @return the group ID.
     */
    String getGroupId();
}
