package com.osearch.crawler.inout.web.controller;

import com.osearch.crawler.inout.web.api.CrawlerApi;
import com.osearch.crawler.inout.web.entity.StartRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CrawlerController implements CrawlerApi {


    @Override
    public void start(StartRequest request) {

    }

    @Override
    public void stop() {

    }
}
