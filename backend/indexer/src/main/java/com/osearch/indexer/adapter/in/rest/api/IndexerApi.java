package com.osearch.indexer.adapter.in.rest.api;

import com.osearch.indexer.adapter.in.rest.entity.ErrorResponse;
import com.osearch.indexer.adapter.in.rest.entity.PagesCountResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This interface represents the API for the Indexer service.
 */
@Validated
@Api(tags = "Indexer API")
@RequestMapping(path = "/api/indexer", produces = "application/json")
public interface IndexerApi {

    @GetMapping("/pages")
    @ApiOperation(value = "Count indexed pages")
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
