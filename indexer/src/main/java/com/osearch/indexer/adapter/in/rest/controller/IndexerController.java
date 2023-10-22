package com.osearch.indexer.adapter.in.rest.controller;

import com.osearch.indexer.adapter.in.rest.api.IndexerApi;
import com.osearch.indexer.adapter.in.rest.entity.PagesCountResponse;
import com.osearch.indexer.application.usecase.IndexerUseCase;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IndexerController implements IndexerApi {
    private final IndexerUseCase useCase;

    @Override
    public PagesCountResponse count() {
        var count = useCase.countIndexed();
        return new PagesCountResponse(count);
    }
}
