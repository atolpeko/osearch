package com.osearch.crawler.inout.web.controller;

import com.osearch.crawler.inout.web.api.URLsApi;
import com.osearch.crawler.inout.web.entity.UrlsCountResponse;
import com.osearch.crawler.service.CrawlerService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class URLsController implements URLsApi {
    private final CrawlerService crawlerService;

    @Override
    public UrlsCountResponse count() {
        var count = crawlerService.urlsProcessed();
        return new UrlsCountResponse(count);
    }
}
