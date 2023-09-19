package com.osearch.crawler.adapter.shared.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.crawler.application.port.exception.MessagingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.core.KafkaTemplate;

/**
 * The BaseMessageSender class is responsible for sending messages to a Kafka topic.
 */
@Log4j2
@RequiredArgsConstructor
public class BaseMessageSender {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Sends a message to the specified Kafka topic.
     *
     * @param topic The topic to send the message to.
     * @param object The object to be sent as the message payload.
     *
     * @throws MessagingException If an error occurs while sending the message.
     */
    protected final void send(String topic, Object object) {
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
