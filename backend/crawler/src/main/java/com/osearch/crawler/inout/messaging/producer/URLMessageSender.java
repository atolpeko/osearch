package com.osearch.crawler.inout.messaging.producer;

import com.osearch.crawler.inout.messaging.entity.URLDto;

/**
 * Used to send URL objects to kafka topic
 */
public interface URLMessageSender {

    /**
     * Send URL objects to kafka topic
     *
     * @param dto  URL to send
     */
    void send(URLDto dto);
}
