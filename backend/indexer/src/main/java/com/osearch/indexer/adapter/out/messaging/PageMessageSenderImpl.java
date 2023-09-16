package com.osearch.indexer.adapter.out.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.indexer.application.exception.MessagingException;
import com.osearch.indexer.application.port.PageMessageSender;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.core.KafkaTemplate;

@Log4j2
@RequiredArgsConstructor
public class PageMessageSenderImpl implements PageMessageSender {
    private final OutMessagingProperties properties;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public synchronized void send(long id) {
        var topic = properties.getOutTopic();
        try {
            log.debug("Sending to kafka topic {}: {}", topic, id);
            var data = objectMapper.writeValueAsString(id);
            kafkaTemplate.send(topic, data);
        } catch (Exception e) {
            var message = "Cannot send message to kafka topic "
                + topic + ": " + e.getMessage();
            throw new MessagingException(message, e);
        }
    }
}