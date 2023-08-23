package com.osearch.crawler.application.port;

import com.osearch.crawler.application.port.exception.MessagingException;
import com.osearch.crawler.domain.entity.Page;

/**
 * Used to send Page objects to messaging queue.
 */
public interface PageMessageSender {

    /**
     * Send Page objects to messaging queue.
     *
     * @param dto  Page to send
     *
     * @throws MessagingException if any messaging error occurred
     */
    void send(Page dto);
}
