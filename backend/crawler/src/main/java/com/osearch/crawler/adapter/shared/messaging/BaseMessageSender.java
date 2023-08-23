package com.osearch.crawler.adapter.shared.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.crawler.application.port.exception.MessagingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.core.KafkaTemplate;

@Log4j2
@RequiredArgsConstructor
public class BaseMessageSender {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    protected void send(String topic, Object object) {
        try {
            log.debug("Sending to kafka topic {}: {}", topic, object.toString());
            var data = objectMapper.writeValueAsString(object);
            kafkaTemplate.send(topic, data);
        } catch (Exception e) {
            var message = "Cannot send message to kafka topic "
                + topic + ": " + e.getMessage();
            throw new MessagingException(message, e);
        }
    }
}
