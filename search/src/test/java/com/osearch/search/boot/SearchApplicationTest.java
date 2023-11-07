package com.osearch.search.boot;

import com.osearch.search.adapter.in.controller.IndexController;
import com.osearch.search.adapter.in.controller.SearchController;
import com.osearch.search.config.IntegrationTest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@Tag("category.IntegrationTest")
class SearchApplicationTest {

    @Autowired
    SearchController searchController;

    @Autowired
    IndexController indexController;

    @Test
    void contextLoads() {

    }
}