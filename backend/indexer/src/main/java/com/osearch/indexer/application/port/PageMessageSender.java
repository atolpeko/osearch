package com.osearch.indexer.application.port;

import com.osearch.indexer.application.exception.MessagingException;
import com.osearch.indexer.domain.entity.Page;

/**
 * Used to send Page objects to messaging queue.
 */
public interface PageMessageSender {

    /**
     * Send Page objects to messaging queue.
     *
     * @param page  page to send
     *
     * @throws MessagingException if any messaging error occurred
     */
    void send(Page page);
}
