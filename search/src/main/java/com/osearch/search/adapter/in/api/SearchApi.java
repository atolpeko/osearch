package com.osearch.search.adapter.in.api;

import com.osearch.search.adapter.in.entity.ErrorResponse;
import com.osearch.search.adapter.in.entity.PagesResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * SearchApi interface represents the API endpoints for searching for pages.
 * <p>
 * All methods in this interface are designed to be used over HTTP.
 */
@Api(tags = "Search API")
@RequestMapping(path = "/api/search/", produces = "application/json")
public interface SearchApi {

    @GetMapping
    @ApiOperation(
        value = "Search for pages based on a search string",
        notes = "Search for a list of pages matching the provided search string. "
            + "The list is limited by the provided limit and offset",
        response = PagesResponse.class
    )
    @ApiResponses(value = {
        @ApiResponse(
            code = 200,
            message = "Successfully retrieved pages",
            response = PagesResponse.class
        ),
        @ApiResponse(
            code = 404,
            message = "No pages found for this search string",
            response = ErrorResponse.class
        ),
        @ApiResponse(
            code = 500,
            message = "Response in case of an unknown internal error",
            response = ErrorResponse.class
        )
    })
    PagesResponse search(
        @ApiParam(
            value = "The string value used for searching relevant pages. "
                + "Accepts alphanumeric and special characters.",
            required = true)
        @RequestParam String searchString,

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
