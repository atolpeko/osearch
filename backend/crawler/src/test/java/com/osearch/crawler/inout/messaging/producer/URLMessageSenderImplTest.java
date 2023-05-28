package com.osearch.crawler.inout.messaging.producer;

import static com.osearch.crawler.fixture.UrlMessageSenderFixture.TOPIC;
import static com.osearch.crawler.fixture.UrlMessageSenderFixture.url;
import static com.osearch.crawler.fixture.UrlMessageSenderFixture.urlJson;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.osearch.crawler.config.properties.KafkaProperties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

@Tag("category.UnitTest")
class URLMessageSenderImplTest {

    @InjectMocks
    URLMessageSenderImpl target;

    @Mock
    KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    KafkaProperties properties;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        when(properties.getUrlTopic()).thenReturn(TOPIC);
        when(objectMapper.writeValueAsString(url())).thenReturn(urlJson());
    }

    @Test
    void shouldSendUrl() {
        target.send(url());
        verify(kafkaTemplate, times(1)).send(TOPIC, urlJson());
    }
}