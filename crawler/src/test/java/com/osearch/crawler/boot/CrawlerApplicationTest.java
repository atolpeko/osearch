package com.osearch.crawler.boot;

import com.osearch.crawler.adapter.in.rest.controller.BaseController;
import com.osearch.crawler.adapter.in.rest.controller.CrawlerController;
import com.osearch.crawler.adapter.in.rest.controller.PageController;
import com.osearch.crawler.config.IntegrationTest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
@Tag("category.IntegrationTest")
class CrawlerApplicationTest {

    @Autowired
    CrawlerController crawlerController;

    @Autowired
    PageController pageController;


    @Autowired
    BaseController baseController;

    @Test
    void contextLoads() {

    }
}