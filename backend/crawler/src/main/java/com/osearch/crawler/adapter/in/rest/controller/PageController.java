package com.osearch.crawler.adapter.in.rest.controller;

import com.osearch.crawler.adapter.in.rest.entity.PagesCountResponse;
import com.osearch.crawler.adapter.in.rest.api.PageAPI;
import com.osearch.crawler.application.usecase.PageUseCase;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PageController implements PageAPI {
    private final PageUseCase pageUseCase;

    @Override
    public PagesCountResponse count() {
        var count = pageUseCase.countProcessed();
        return new PagesCountResponse(count);
    }
}
