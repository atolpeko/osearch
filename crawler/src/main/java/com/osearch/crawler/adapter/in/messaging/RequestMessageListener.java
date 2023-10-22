package com.osearch.crawler.adapter.in.messaging;

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

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * The RequestMessageListener class is responsible for listening to messages from a Kafka topic,
 * processing the messages, and sending corresponding response messages.
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class RequestMessageListener {
    private final CrawlerUseCase crawler;
    private final ResponseMessageSender responseMessageSender;
    private final RequestMapper mapper;
    private final InMessagingProperties properties;

    /**
     * Listens for incoming messages from Kafka and processes them.
     *
     * @param message The message received from Kafka.
     */
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
        } catch (IllegalArgumentException e) {
            sendError(message + " " + e.getMessage());
        } catch (CrawlerAlreadyRunningException e) {
            sendError("Crawler is already running");
        } catch (CrawlerNotRunningException e) {
            sendError("Crawler is not running");
        } catch (MessagingException e) {
            log.error("Error sending response message: {}", e.getMessage());
        } catch (Exception e) {
            sendError(e.getMessage());
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
