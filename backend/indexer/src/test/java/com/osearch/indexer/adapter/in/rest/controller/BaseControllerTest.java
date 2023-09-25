package com.osearch.indexer.adapter.in.rest.controller;

import static com.osearch.indexer.fixture.BaseControllerFixture.REDIRECT_URL;
import static com.osearch.indexer.fixture.BaseControllerFixture.URL;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.osearch.indexer.config.IntegrationTest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@Tag("category.IntegrationTest")
@IntegrationTest
class BaseControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void shouldRedirectToSwagger() throws Exception {
        mvc.perform(get(URL))
            .andDo(print())
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl(REDIRECT_URL));
    }
}