package com.osearch.indexer.adapter.in;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.indexer.application.usecase.IndexerUseCase;
import com.osearch.indexer.domain.entity.IndexRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class RequestMessageListener {
    private final ObjectMapper objectMapper;
    private final IndexerUseCase useCase;
    private final InMessagingProperties properties;

    @KafkaListener(
        topics = "#{messagingProperties.getTopic()}",
        groupId = "#{messagingProperties.getGroupId()}"
    )
    public void listen(String message) {
        try {
            log.debug("Receiving a message from topic {}", properties.getTopic());
            var request = toIndexRequest(message);
            useCase.process(request);
        } catch (JsonMappingException e) {
            log.error("Message doesn't match the structure of request");
        } catch (JsonProcessingException e) {
            log.error("Message is not a valid JSON");
        } catch (IllegalArgumentException e) {
            log.debug("Message is not a valid request");
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
        }
    }

    public IndexRequest toIndexRequest(String message) throws JsonProcessingException {
        return objectMapper.readValue(message, IndexRequest.class);
    }
}
