package com.osearch.search.adapter.in.controller;

import com.osearch.search.adapter.in.api.IndexApi;
import com.osearch.search.adapter.in.entity.IndexInfoResponse;
import com.osearch.search.adapter.in.mapper.IndexInfoMapper;
import com.osearch.search.application.usecase.IndexUseCase;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IndexController implements IndexApi {
    private final IndexUseCase useCase;
    private final IndexInfoMapper mapper;

    @Override
    public IndexInfoResponse getInfo(Integer offset, Integer limit) {
        var result = useCase.getInfo(offset, limit);
        var next = result.isHasNextPage() ? offset + limit : null;
        return IndexInfoResponse.builder()
            .indexToPagesCount(mapper.map(result.getValues()))
            .nextOffset(next)
            .lastOffset(offset)
            .lastLimit(limit)
            .build();
    }
}
