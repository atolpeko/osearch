package com.osearch.crawler.inout.web.controller;

import com.osearch.crawler.inout.web.api.CrawlerApi;
import com.osearch.crawler.inout.web.entity.IsRunningResponse;
import com.osearch.crawler.inout.web.entity.StartRequest;
import com.osearch.crawler.service.CrawlerService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CrawlerController implements CrawlerApi {
    private final CrawlerService crawlerService;

    @Override
    public void start(StartRequest request) {
        var urls = request.getUrls();
        crawlerService.start(urls);
    }

    @Override
    public void stop() {
        crawlerService.stop();
    }

    @Override
    public IsRunningResponse isRunning() {
        var isRunning = crawlerService.isRunning();
        return new IsRunningResponse(isRunning);
    }
}
