package com.osearch.crawler.adapter.in.rest.api;

import com.osearch.crawler.adapter.in.rest.entity.ErrorResponse;
import com.osearch.crawler.adapter.in.rest.entity.PagesCountResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * PageAPI interface represents the API endpoints for pages-related operations.
 * <p>
 * All methods in this interface are designed to be used over HTTP.
 */
@Api(tags = "Pages API", protocols = "http")
@RequestMapping(path = "/api/pages", produces = "application/json")
public interface PageAPI {

    @GetMapping("/count")
    @ApiOperation(
        value = "Count crawled pages",
        notes = "This API endpoint returns the total count"
            + " of the pages that have been crawled",
        response = PagesCountResponse.class
    )
    @ApiResponses(value = {
        @ApiResponse(
            code = 200,
            message = "Successfully retrieved the pages count",
            response = PagesCountResponse.class
        ),
        @ApiResponse(
            code = 500,
            message = "Response in case of an unknown internal error",
            response = ErrorResponse.class
        )
    })
    PagesCountResponse count();
}
