package com.osearch.crawler.inout.web.controller;

import static com.osearch.crawler.fixture.CrawlerControllerFixture.IS_RUNNING_URL;
import static com.osearch.crawler.fixture.CrawlerControllerFixture.START_URL;
import static com.osearch.crawler.fixture.CrawlerControllerFixture.STOP_URL;
import static com.osearch.crawler.fixture.CrawlerControllerFixture.initialUrls;
import static com.osearch.crawler.fixture.CrawlerControllerFixture.startRequestJson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.osearch.crawler.config.IntegrationTest;
import com.osearch.crawler.service.CrawlerService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Tag("category.IntegrationTest")
@IntegrationTest
@AutoConfigureMockMvc
class CrawlerControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    CrawlerService service;

    @AfterEach
    void cleanUp() {
        if (service.isRunning()) {
            service.stop();
        }
    }

    @Test
    void shouldStartService() throws Exception {
        mvc.perform(post(START_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(startRequestJson()))
                .andDo(print())
                .andExpect(status().isOk());

        assertTrue(service.isRunning());
    }

    @Test
    void shouldNotStartServiceWhenItsRunning() throws Exception {
        service.start(initialUrls());
        mvc.perform(post(START_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(startRequestJson()))
                .andDo(print())
                .andExpect(status().isBadRequest());

        assertTrue(service.isRunning());
    }

    @Test
    void shouldStopService() throws Exception {
        service.start(initialUrls());
        mvc.perform(delete(STOP_URL))
                .andDo(print())
                .andExpect(status().isOk());

        assertFalse(service.isRunning());
    }

    @Test
    void shouldNotStopServiceWhenItsNotRunning() throws Exception {
        mvc.perform(delete(STOP_URL))
                .andDo(print())
                .andExpect(status().isBadRequest());

        assertFalse(service.isRunning());
    }

    @Test
    void shouldReturnRunningStatus() throws Exception {
        service.start(initialUrls());
        mvc.perform(get(IS_RUNNING_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.isRunning").value(true));
    }
}