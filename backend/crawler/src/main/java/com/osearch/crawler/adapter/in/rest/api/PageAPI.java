package com.osearch.crawler.adapter.in.rest.api;

import com.osearch.crawler.adapter.in.rest.entity.PagesCountResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Api(tags = "Pages API")
@RequestMapping(path = "/pages", produces = "application/json")
public interface PageAPI {

    @GetMapping("/count")
    @ApiOperation(value = "Get processed pages count")
    PagesCountResponse count();
}
