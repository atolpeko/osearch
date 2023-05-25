package com.osearch.crawler.inout.messaging.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.osearch.crawler.config.properties.KafkaProperties;
import com.osearch.crawler.inout.messaging.entity.Request;
import com.osearch.crawler.inout.messaging.entity.Request.Operation;
import com.osearch.crawler.inout.messaging.entity.Response;
import com.osearch.crawler.inout.messaging.entity.Response.Status;
import com.osearch.crawler.inout.messaging.producer.ResponseMessageSender;
import com.osearch.crawler.inout.messaging.validator.RequestValidator;
import com.osearch.crawler.service.CrawlerService;

import com.osearch.crawler.service.exception.CrawlerAlreadyRunningException;
import com.osearch.crawler.service.exception.CrawlerNotRunningException;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class RequestMessageListener {
    private final ObjectMapper objectMapper;
    private final RequestValidator validator;
    private final ResponseMessageSender responseMessageSender;
    private final CrawlerService crawlerService;

    @KafkaListener(
            topics = "#{kafkaProperties.getRequestTopic()}",
            groupId = "#{kafkaProperties.getGroupId()}"
    )
    public void listen(String message) {
        try {
            log.info("Receiving a message from topic {}: {} ",
                    KafkaProperties.getRequestTopic(), message);
            var request = toRequest(message);
            process(request);
        } catch (JsonMappingException e) {
            sendRequestError(message + " doesn't match the structure of StartRequest");
        } catch (JsonProcessingException e) {
            sendRequestError(message + " is not a valid JSON");
        } catch (IllegalArgumentException e) {
            sendRequestError(message + " is not a valid request");
        } catch (CrawlerAlreadyRunningException e) {
            sendRequestError("Crawler is already running");
        } catch (CrawlerNotRunningException e) {
            sendRequestError("Crawler is not running");
        }
    }

    private Request toRequest(String message) throws JsonProcessingException {
        var request = objectMapper.readValue(message, Request.class);
        if (!validator.isValid(request)) {
            throw new IllegalArgumentException();
        }

        return request;
    }

    private void process(Request request) {
        var operation = request.getOperation();
        if (operation.equals(Operation.START)) {
            var urls = request.getUrls();
            crawlerService.start(urls);
        } else if (operation.equals(Operation.STOP)) {
            crawlerService.stop();
        }

        sentRequestSuccess();
    }

    private void sentRequestSuccess() {
        var response = Response.builder()
                .status(Status.SUCCESSFUL)
                .requestDateTime(LocalDateTime.now())
                .build();

        responseMessageSender.send(response);
    }

    private void sendRequestError(String message) {
        log.info("Start request error: {}", message);
        var response = Response.builder()
                .status(Status.ERROR)
                .requestDateTime(LocalDateTime.now())
                .description(message)
                .build();

        responseMessageSender.send(response);
    }
}
