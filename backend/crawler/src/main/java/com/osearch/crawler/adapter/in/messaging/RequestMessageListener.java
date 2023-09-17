package com.osearch.crawler.adapter.in.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.osearch.crawler.adapter.in.messaging.properties.InMessagingProperties;
import com.osearch.crawler.adapter.in.messaging.entity.Request;
import com.osearch.crawler.adapter.in.messaging.entity.Request.Operation;
import com.osearch.crawler.adapter.in.messaging.entity.Response;
import com.osearch.crawler.adapter.in.messaging.entity.Response.Status;
import com.osearch.crawler.adapter.in.messaging.mapper.RequestMapper;
import com.osearch.crawler.application.port.exception.MessagingException;
import com.osearch.crawler.application.usecase.CrawlerUseCase;
import com.osearch.crawler.application.usecase.exception.CrawlerAlreadyRunningException;
import com.osearch.crawler.application.usecase.exception.CrawlerNotRunningException;
import com.osearch.crawler.application.usecase.exception.CrawlerServiceException;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class RequestMessageListener {
    private final CrawlerUseCase crawler;
    private final ResponseMessageSender responseMessageSender;
    private final RequestMapper mapper;
    private final InMessagingProperties properties;

    @KafkaListener(
        topics = "#{messageProperties.getRequestTopic()}",
        groupId = "#{messageProperties.getGroupId()}"
    )
    public void listen(String message) {
        try {
            log.info("Receiving a message from topic {}: {} ",
                    properties.getRequestTopic(), message);
            var request = mapper.toRequest(message);
            process(request);
            sendSuccess();
        } catch (JsonMappingException e) {
            sendError(message + " doesn't match the structure of StartRequest");
        } catch (JsonProcessingException e) {
            sendError(message + " is not a valid JSON");
        } catch (IllegalArgumentException e) {
            sendError(message + " is not a valid request");
        } catch (CrawlerAlreadyRunningException e) {
            sendError("Crawler is already running");
        } catch (CrawlerNotRunningException e) {
            sendError("Crawler is not running");
        } catch (CrawlerServiceException e) {
            sendError(e.getMessage());
        } catch (MessagingException e) {
            log.error("Error sending message: {}", e.getMessage());
        }
    }

    private void process(Request request) {
        var operation = request.getOperation();
        if (operation.equals(Operation.START)) {
            var urls = request.getUrls();
            crawler.start(urls);
        } else if (operation.equals(Operation.STOP)) {
            crawler.stop();
        }
    }

    private void sendSuccess() {
        var response = Response.builder()
                .status(Status.SUCCESSFUL)
                .requestDateTime(LocalDateTime.now())
                .build();

        responseMessageSender.send(response);
    }

    private void sendError(String message) {
        log.info("Start request error: {}", message);
        var response = Response.builder()
                .status(Status.ERROR)
                .requestDateTime(LocalDateTime.now())
                .description(message)
                .build();

        responseMessageSender.send(response);
    }
}
