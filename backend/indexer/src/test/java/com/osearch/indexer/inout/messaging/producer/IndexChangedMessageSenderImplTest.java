package com.osearch.indexer.inout.messaging.producer;

import static com.osearch.indexer.fixture.IndexChangedMessageSenderFixture.ID;
import static com.osearch.indexer.fixture.IndexChangedMessageSenderFixture.TOPIC;
import static com.osearch.indexer.fixture.IndexChangedMessageSenderFixture.message;

import static com.osearch.indexer.fixture.IndexChangedMessageSenderFixture.request;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.indexer.config.properties.KafkaProperties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.kafka.core.KafkaTemplate;

@Tag("category.UnitTest")
class IndexChangedMessageSenderImplTest {

    @InjectMocks
    IndexChangedMessageSenderImpl target;

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    KafkaProperties properties;

    @Mock
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        when(properties.getRequestTopic()).thenReturn(TOPIC);
        when(objectMapper.writeValueAsString(request())).thenReturn(message());
    }

    @Test
    void shouldSendMessage() {
        target.send(ID);
        verify(kafkaTemplate, times(1)).send(TOPIC, message());
    }
}