package com.osearch.indexer.adapter.in.rest.api;

import com.osearch.indexer.adapter.in.rest.entity.ErrorResponse;
import com.osearch.indexer.adapter.in.rest.entity.PagesCountResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * IndexerApi interface represents the API endpoints for consuming
 * information about pages that have been indexed.
 * <p>
 * All methods in this interface are designed to be used over HTTP.
 */
@Api(tags = "Indexer API")
@RequestMapping(path = "/api/indexer", produces = "application/json")
public interface IndexerApi {

    @GetMapping("/pages")
    @ApiOperation(
        value = "Count indexed pages",
        notes = "API to get the total count of indexed pages",
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
