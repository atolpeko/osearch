package com.osearch.crawler.inout.web.controller;

import static com.osearch.crawler.fixture.UrlsControllerFixture.URL;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.osearch.crawler.config.IntegrationTest;
import com.osearch.crawler.service.CrawlerService;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Tag("category.IntegrationTest")
@IntegrationTest
@AutoConfigureMockMvc
class URLsControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    CrawlerService service;

    @Test
    void shouldReturnUrlsCount() throws Exception {
        var count = service.urlsProcessed();
        mvc.perform(get(URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(count));
    }
}