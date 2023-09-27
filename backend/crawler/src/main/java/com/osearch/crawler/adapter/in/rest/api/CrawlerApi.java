package com.osearch.crawler.adapter.in.rest.api;

import com.osearch.crawler.adapter.in.rest.entity.ErrorResponse;
import com.osearch.crawler.adapter.in.rest.entity.IsRunningResponse;
import com.osearch.crawler.adapter.in.rest.entity.StartRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The CrawlerApi interface represents an API for controlling crawler.
 * <p>
 * All methods in this interface are designed to be used over HTTP.
 */
@Validated
@Api(tags = "Crawler API", protocols = "http")
@RequestMapping(path = "/api/crawler", produces = "application/json")
public interface CrawlerApi {

    @PostMapping("/start")
    @ApiOperation(
        value = "Start this crawler",
        notes = "This endpoint initiates the web crawling process,"
            + " beginning from an initial URL list"
            + " input which is provided inside the StartRequest object"
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The crawler started successfully"),
        @ApiResponse(
            code = 400,
            message = "Response in case of invalid StartRequest. "
                + "The client is expected to modify the request before retrying.",
            response = ErrorResponse.class
        ),
        @ApiResponse(
            code = 417,
            message = "Response in case if this crawler is already running. "
                + "The client is expected to stop the crawler before"
                + " issuing a new start request",
            response = ErrorResponse.class
        ),
        @ApiResponse(
            code = 500,
            message = "Response in case of an unknown internal error",
            response = ErrorResponse.class
        )
    })
    void start(
        @ApiParam(value = "The request object to start the crawler", required = true)
        @RequestBody @Valid StartRequest request
    );

    @DeleteMapping("/stop")
    @ApiOperation(
        value = "Stop this crawler",
        notes = "The crawler will stop looking for new pages"
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "The crawler stopped successfully"),
        @ApiResponse(
            code = 417,
            message = "Response in case if the crawler is not running. "
                + "The client is expected to start the crawler before"
                + " issuing a new stop request",
            response = ErrorResponse.class
        ),
        @ApiResponse(
            code = 500,
            message = "Response in case of an unknown internal error",
            response = ErrorResponse.class
        )
    })
    void stop();

    @GetMapping("/running")
    @ApiOperation(
        value = "Check if crawler is running",
        notes = "This endpoint checks the current running status of the crawler",
        response = IsRunningResponse.class
    )
    @ApiResponses(value = {
        @ApiResponse(
            code = 200,
            message = "Successfully retrieved the running status",
            response = IsRunningResponse.class
        ),
        @ApiResponse(
            code = 500,
            message = "Response in case of an unknown internal error",
            response = ErrorResponse.class
        )
    })
    IsRunningResponse isRunning();
}
