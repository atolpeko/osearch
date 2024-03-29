package com.osearch.indexer.adapter.out.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.indexer.adapter.out.messaging.mapper.PageMapper;
import com.osearch.indexer.adapter.out.messaging.properties.OutMessagingProperties;
import com.osearch.indexer.application.port.exception.MessagingException;
import com.osearch.indexer.application.port.PageMessageSender;
import com.osearch.indexer.domain.entity.Page;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.core.KafkaTemplate;

@Log4j2
@RequiredArgsConstructor
public class PageMessageSenderImpl implements PageMessageSender {
    private final OutMessagingProperties properties;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final PageMapper mapper;
    private final ObjectMapper objectMapper;

    @Override
    public synchronized void send(Page page) {
        var topic = properties.getOutTopic();
        try {
            var dto = mapper.toDto(page);
            log.debug("Sending to kafka topic {}: {}", topic, dto);
            var data = objectMapper.writeValueAsString(dto);
            kafkaTemplate.send(topic, data);
        } catch (Exception e) {
            var message = "Cannot send message to kafka topic "
                + topic + ": " + e.getMessage();
            throw new MessagingException(message, e);
        }
    }
}