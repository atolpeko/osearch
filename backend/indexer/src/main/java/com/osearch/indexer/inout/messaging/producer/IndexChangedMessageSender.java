package com.osearch.indexer.inout.messaging.producer;

/**
 * Used to send messages about changed index to kafka.
 */
public interface IndexChangedMessageSender {

    /**
     * Send a message about changed index to kafka.
     *
     * @param pageId  ID of the page changed
     */
    void send(long pageId);
}
