package com.osearch.search.adapter.in.controller;

import com.osearch.search.adapter.in.api.SearchApi;
import com.osearch.search.adapter.in.entity.PagesResponse;
import com.osearch.search.adapter.in.mapper.PageMapper;
import com.osearch.search.application.usecase.SearchUseCase;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController implements SearchApi {
    private final SearchUseCase useCase;
    private final PageMapper mapper;

    @Override
    public PagesResponse search(String searchString, Integer offset, Integer limit) {
        var result = useCase.search(searchString, offset, limit);
        var next = result.isHasNextPage() ? offset + limit : null;
        var pages = result.getValues().stream()
            .map(mapper::toResponse)
            .collect(Collectors.toList());

        return PagesResponse.builder()
            .pages(pages)
            .nextOffset(next)
            .lastOffset(offset)
            .lastLimit(limit)
            .build();
    }
}
