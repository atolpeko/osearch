package com.osearch.indexer.inout.messaging.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.indexer.config.properties.KafkaProperties;
import com.osearch.indexer.inout.messaging.entity.NewUrlRequest;
import com.osearch.indexer.inout.messaging.mapper.IndexRequestMapper;
import com.osearch.indexer.service.IndexerService;

import javax.validation.Validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class RequestMessageListener {
    private final ObjectMapper objectMapper;
    private final Validator validator;
    private final IndexRequestMapper mapper;
    private final IndexerService indexerService;

    private final KafkaProperties properties;

    @KafkaListener(
            topics = "#{kafkaProperties.getUrlTopic()}",
            groupId = "#{kafkaProperties.getGroupId()}"
    )
    public void listen(String message) {
        try {
            log.debug("Receiving a message from topic {}", properties.getRequestTopic());
            var request = toRequest(message);
            process(request);
        } catch (JsonMappingException e) {
            log.error("Message doesn't match the structure of NewUrlRequest");
        } catch (JsonProcessingException e) {
            log.error("Message is not a valid JSON");
        } catch (IllegalArgumentException e) {
            log.debug("Message is not a valid NewUrlRequest");
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
        }
    }

    private NewUrlRequest toRequest(String message) throws JsonProcessingException {
        var request = objectMapper.readValue(message, NewUrlRequest.class);
        if (!validator.validate(request).isEmpty()) {
            throw new IllegalArgumentException();
        }

        return request;
    }

    private void process(NewUrlRequest request) {
        var indexRequest = mapper.toServiceRequest(request);
        indexerService.process(indexRequest);
    }
}
