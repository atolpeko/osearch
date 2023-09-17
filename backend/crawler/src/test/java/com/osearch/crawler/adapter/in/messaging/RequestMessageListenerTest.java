package com.osearch.crawler.adapter.in.messaging;

import static com.osearch.crawler.fixture.RequestMessageListenerFixture.TOPIC;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.crawlerAlreadyRunningResponse;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.crawlerNotRunningResponse;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.invalidStartRequestResponse;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.initialUrls;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.invalidRequestJson;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.invalidStartRequestJson;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.invalidStopRequestResponse;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.startRequest;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.startRequestJson;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.stopRequest;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.stopRequestJson;
import static com.osearch.crawler.fixture.RequestMessageListenerFixture.successResponseJson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.osearch.crawler.adapter.in.messaging.mapper.RequestMapper;
import com.osearch.crawler.adapter.in.messaging.properties.InMessagingProperties;
import com.osearch.crawler.application.usecase.exception.CrawlerAlreadyRunningException;
import com.osearch.crawler.application.usecase.exception.CrawlerNotRunningException;
import com.osearch.crawler.application.usecase.CrawlerUseCase;

import lombok.extern.log4j.Log4j2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Log4j2
@Tag("category.UnitTest")
class RequestMessageListenerTest {

    @InjectMocks
    RequestMessageListener target;

    @Mock
    CrawlerUseCase useCase;

    @Mock
    ResponseMessageSender messageSender;

    @Mock
    RequestMapper mapper;

    @Mock
    InMessagingProperties properties;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        when(properties.getRequestTopic()).thenReturn(TOPIC);
        when(mapper.toRequest(startRequestJson())).thenReturn(startRequest());
        when(mapper.toRequest(stopRequestJson())).thenReturn(stopRequest());
        when(mapper.toRequest(invalidStartRequestJson())).thenThrow(IllegalArgumentException.class);
        when(mapper.toRequest(invalidRequestJson())).thenThrow(IllegalArgumentException.class);
    }

    @Test
    void shouldStartCrawlerWhenRequestIsValid() {
        doAnswer(i -> when(useCase.isRunning()).thenReturn(true))
            .when(useCase).start(initialUrls());

        target.listen(startRequestJson());
        assertTrue(useCase.isRunning());
    }

    @Test
    void shouldSendResponseWhenStartsCrawler() {
        target.listen(startRequestJson());
        verify(messageSender, times(1)).send(successResponseJson());
    }

    @Test
    void shouldStopCrawlerWhenRequestIsValid() {
        doAnswer(i -> when(useCase.isRunning()).thenReturn(true))
            .when(useCase).start(initialUrls());
        doAnswer(i -> when(useCase.isRunning()).thenReturn(false))
            .when(useCase).stop();

        useCase.start(initialUrls());
        target.listen(stopRequestJson());

        assertFalse(useCase.isRunning());
    }

    @Test
    void shouldSendResponseWhenStopsCrawler() {
        useCase.start(initialUrls());
        target.listen(stopRequestJson());

        verify(messageSender, times(1)).send(successResponseJson());
    }

    @Test
    void shouldSendResponseWhenStartRequestIsInvalid() {
        target.listen(invalidStartRequestJson());
        verify(messageSender, times(1)).send(invalidStartRequestResponse());
    }

    @Test
    void shouldSendResponseWhenStopRequestIsInvalid() {
        target.listen(invalidRequestJson());
        verify(messageSender, times(1)).send(invalidStopRequestResponse());
    }

    @Test
    void shouldSendResponseWhenCrawlerAlreadyRunning() {
        doThrow(CrawlerAlreadyRunningException.class).when(useCase).start(initialUrls());

        target.listen(startRequestJson());
        verify(messageSender, times(1)).send(crawlerAlreadyRunningResponse());
    }

    @Test
    void shouldSendResponseWhenCrawlerNotRunning() {
        doThrow(CrawlerNotRunningException.class).when(useCase).stop();

        target.listen(stopRequestJson());
        verify(messageSender, times(1)).send(crawlerNotRunningResponse());
    }
}