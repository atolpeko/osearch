package com.osearch.ranker.adapter.in;

import com.osearch.ranker.adapter.in.mapper.RequestMapper;
import com.osearch.ranker.adapter.in.properties.InMessagingProperties;
import com.osearch.ranker.application.usecase.RankerUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * The NewPageListener class is responsible for listening to new page messages
 * from Kafka and processing them using the RankerUseCase.
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class NewPageListener {
    private final RankerUseCase useCase;
    private final RequestMapper mapper;
    private final InMessagingProperties properties;

    @KafkaListener(
        topics = "#{messagingProperties.getTopic()}",
        groupId = "#{messagingProperties.getGroupId()}"
    )
    public void listen(String message) {
        try {
            log.debug("Receiving a message from topic {}", properties.getTopic());
            var request = mapper.toRequest(message);
            useCase.process(request.getId());
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error("Error: {}", e.getMessage());
        }
    }
}
