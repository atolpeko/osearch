package com.osearch.crawler.inout.messaging.producer;

import com.osearch.crawler.inout.messaging.entity.Response;

/**
 * Used to send Response objects to kafka topic
 */
public interface ResponseMessageSender {

    /**
     * Send Response objects to kafka topic
     *
     * @param response  responce to send
     */
    void send(Response response);
}
