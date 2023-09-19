package com.osearch.crawler.adapter.in.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.crawler.adapter.in.messaging.properties.InMessagingProperties;
import com.osearch.crawler.adapter.in.messaging.entity.Response;
import com.osearch.crawler.adapter.shared.messaging.BaseMessageSender;

import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.core.KafkaTemplate;

/**
 * A class responsible for sending response messages to kafka.
 */
@Log4j2
public class ResponseMessageSender extends BaseMessageSender {
    private final InMessagingProperties properties;

    public ResponseMessageSender(
        KafkaTemplate<String, String> kafkaTemplate,
        ObjectMapper objectMapper,
        InMessagingProperties properties
    ) {
        super(kafkaTemplate, objectMapper);
        this.properties = properties;
    }

    /**
     * Sends a response to a specific topic.
     *
     * @param response The response object to be sent.
     */
    public void send(Response response) {
        var topic = properties.getResponseTopic();
        super.send(topic, response);
    }
}