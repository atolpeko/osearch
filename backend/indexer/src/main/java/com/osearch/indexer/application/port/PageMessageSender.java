package com.osearch.indexer.application.port;

import com.osearch.indexer.application.exception.MessagingException;

/**
 * Used to send Page objects to messaging queue.
 */
public interface PageMessageSender {

    /**
     * Send page ID to messaging queue.
     *
     * @param id  page ID to send
     *
     * @throws MessagingException if any messaging error occurred
     */
    void send(long id);
}
