package com.osearch.crawler.inout.web.api;

import com.osearch.crawler.inout.web.entity.UrlsCountResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/urls", produces = "application/json")
@Api(tags = "URLs API")
@Validated
public interface URLsApi {

    @GetMapping("/count")
    @ApiOperation(value = "Get processed URLs count")
    UrlsCountResponse count();
}
