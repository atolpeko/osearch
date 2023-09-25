package com.osearch.indexer.application.port;

import com.osearch.indexer.application.exception.MessagingException;

/**
 * PageMessageSender is an interface for sending page IDs to a messaging queue.
 */
public interface PageMessageSender {

    /**
     * Send page ID to messaging queue.
     *
     * @param id  page ID to send.
     *
     * @throws MessagingException if any messaging error occurred.
     */
    void send(long id);
}
