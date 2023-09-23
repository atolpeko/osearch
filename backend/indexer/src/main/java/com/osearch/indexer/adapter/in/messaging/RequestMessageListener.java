package com.osearch.indexer.adapter.in.messaging;

import com.osearch.indexer.adapter.in.messaging.mapper.IndexRequestMapper;
import com.osearch.indexer.application.usecase.IndexerUseCase;
import com.osearch.indexer.domain.entity.IndexRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * This class is a message listener for processing request messages.
 * The class listens to a Kafka topic and processes received messages.
 * The messages are expected to be in JSON format representing an {@link IndexRequest} object.
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class RequestMessageListener {
    private final IndexRequestMapper mapper;
    private final IndexerUseCase useCase;
    private final InMessagingProperties properties;

    @KafkaListener(
        topics = "#{messagingProperties.getTopic()}",
        groupId = "#{messagingProperties.getGroupId()}"
    )
    public void listen(String message) {
        try {
            log.debug("Receiving a message from topic {}", properties.getTopic());
            var request = mapper.map(message);
            useCase.process(request);
        } catch (IllegalArgumentException e) {
            log.debug(message + " " + e.getMessage());
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
        }
    }
}
