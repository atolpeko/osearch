package com.osearch.indexer.adapter.in.rest.controller;

import static com.osearch.indexer.fixture.IndexerControllerFixture.URL;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.osearch.indexer.application.usecase.IndexerUseCase;
import com.osearch.indexer.config.IntegrationTest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Tag("category.IntegrationTest")
@IntegrationTest
@AutoConfigureMockMvc
class IndexerControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    IndexerUseCase useCase;

    @Test
    void shouldReturnUrlsCount() throws Exception {
        var count = useCase.countIndexed();
        mvc.perform(get(URL))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.count").value(count));
    }
}