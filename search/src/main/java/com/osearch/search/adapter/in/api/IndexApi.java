package com.osearch.search.adapter.in.api;

import com.osearch.search.adapter.in.entity.ErrorResponse;
import com.osearch.search.adapter.in.entity.IndexInfoResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * IndexApi interface represents the API endpoints for quering
 * information about stored indexes.
 * <p>
 * All methods in this interface are designed to be used over HTTP.
 */
@Api(tags = "Index API")
@RequestMapping(path = "/api/search/", produces = "application/json")
public interface IndexApi {

    @GetMapping("indexes")
    @ApiOperation(
        value = "Retrieve indexes information",
        notes = "Retrieve a map of indexes to the number of pages for each index. "
            + "The list is limited by the provided limit and offset",
        response = IndexInfoResponse.class
    )
    @ApiResponses(value = {
        @ApiResponse(
            code = 200,
            message = "Successfully retrieved indexes information",
            response = IndexInfoResponse.class
        ),
        @ApiResponse(
            code = 404,
            message = "No indexes found",
            response = ErrorResponse.class
        ),
        @ApiResponse(
            code = 500,
            message = "Response in case of an unknown internal error",
            response = ErrorResponse.class
        )
    })
    IndexInfoResponse getInfo(
        @ApiParam(
            value = "Defines the number of initial records to skip in the result set",
            required = true
        )
        @RequestParam Integer offset,

        @ApiParam(
            value = "Defines the maximum number of records to return in the response",
            required = true
        )
        @RequestParam Integer limit
    );
}
