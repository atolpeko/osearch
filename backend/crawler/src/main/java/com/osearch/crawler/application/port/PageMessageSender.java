package com.osearch.crawler.application.port;

import com.osearch.crawler.application.port.exception.MessagingException;
import com.osearch.crawler.domain.entity.Page;

/**
 * The PageMessageSender interface is used for sending Page objects to a messaging queue.
 */
public interface PageMessageSender {

    /**
     * Send Page objects to messaging queue.
     *
     * @param page Page to send
     *
     * @throws MessagingException if any messaging error occurred
     */
    void send(Page page);
}
