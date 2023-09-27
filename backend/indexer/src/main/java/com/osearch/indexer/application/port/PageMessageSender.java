package com.osearch.indexer.application.port;

import com.osearch.indexer.application.port.exception.MessagingException;
import com.osearch.indexer.domain.entity.Page;

/**
 * PageMessageSender is an interface for sending pages to a messaging queue.
 */
public interface PageMessageSender {

    /**
     * Send page to messaging queue.
     *
     * @param page to send.
     *
     * @throws MessagingException if any messaging error occurred.
     */
    void send(Page page);
}
