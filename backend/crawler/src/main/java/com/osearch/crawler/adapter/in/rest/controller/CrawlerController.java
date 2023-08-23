package com.osearch.crawler.adapter.in.rest.controller;

import com.osearch.crawler.adapter.in.rest.entity.StartRequest;
import com.osearch.crawler.adapter.in.rest.api.CrawlerApi;
import com.osearch.crawler.adapter.in.rest.entity.IsRunningResponse;
import com.osearch.crawler.application.usecase.CrawlerUseCase;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CrawlerController implements CrawlerApi {
    private final CrawlerUseCase crawler;

    @Override
    public void start(StartRequest request) {
        var urls = request.getUrls();
        crawler.start(urls);
    }

    @Override
    public void stop() {
        crawler.stop();
    }

    @Override
    public IsRunningResponse isRunning() {
        var isRunning = crawler.isRunning();
        return new IsRunningResponse(isRunning);
    }
}
