package com.osearch.crawler.adapter.in.rest.api;

import com.osearch.crawler.adapter.in.rest.entity.ErrorResponse;
import com.osearch.crawler.adapter.in.rest.entity.PagesCountResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Api(tags = "Pages API")
@RequestMapping(path = "/pages", produces = "application/json")
public interface PageAPI {

    @GetMapping("/count")
    @ApiOperation(value = "Count processed pages")
    @ApiResponses(value = {
        @ApiResponse(
            code = 200,
            message = "Success response.",
            response = PagesCountResponse.class
        ),
        @ApiResponse(
            code = 500,
            message = "Response in case of an unknown error.",
            response = ErrorResponse.class
        )
    })
    PagesCountResponse count();
}
