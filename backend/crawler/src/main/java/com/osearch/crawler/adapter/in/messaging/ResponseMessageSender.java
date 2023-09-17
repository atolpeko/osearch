package com.osearch.crawler.adapter.in.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osearch.crawler.adapter.in.messaging.properties.InMessagingProperties;
import com.osearch.crawler.adapter.in.messaging.entity.Response;
import com.osearch.crawler.adapter.shared.messaging.BaseMessageSender;

import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.core.KafkaTemplate;

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

    public void send(Response response) {
        var topic = properties.getResponseTopic();
        super.send(topic, response);
    }
}