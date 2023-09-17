package com.osearch.ranker.adapter.in;

import com.osearch.ranker.application.usecase.RankerUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class NewPageListener {
    private final RankerUseCase useCase;
    private final InMessagingProperties properties;

    @KafkaListener(
        topics = "#{messagingProperties.getTopic()}",
        groupId = "#{messagingProperties.getGroupId()}"
    )
    public void listen(String message) {
        try {
            log.debug("Receiving a message from topic {}", properties.getTopic());
            useCase.process(Long.parseLong(message));
        } catch (NumberFormatException e) {
            log.error("Invalid ID format: " + message);
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
        }
    }
}
