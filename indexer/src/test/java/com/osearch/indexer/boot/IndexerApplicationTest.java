package com.osearch.indexer.boot;

import com.osearch.indexer.adapter.in.rest.controller.IndexerController;
import com.osearch.indexer.config.IntegrationTest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@Tag("category.IntegrationTest")
class IndexerApplicationTest {

    @Autowired
    IndexerController controller;

    @Test
    void contextLoads() {

    }
}