package com.osearch.crawler.inout.web.api;

import com.osearch.crawler.inout.web.entity.IsRunningResponse;
import com.osearch.crawler.inout.web.entity.StartRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
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

@RequestMapping(path = "/crawler", produces = "application/json")
@Api(tags = "Crawler API")
@Validated
public interface CrawlerApi {

    @PostMapping("/start")
    @ApiOperation(
            value = "Start crawler",
            notes = "Crawler will look for URLs starting from initial URL"
                    + " list provided in StartRequest object"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Crawler started successfully"),
            @ApiResponse(code = 400, message = "StartRequest is invalid"),
            @ApiResponse(code = 420, message = "Crawler is already running")
    })
    void start(
            @ApiParam(value = "Crawler start request entity", required = true)
            @RequestBody @Valid StartRequest request
    );

    @DeleteMapping("/stop")
    @ApiOperation(value = "Stop crawler", notes = "Crawler will stop looking for URLs")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Crawler stopped successfully"),
            @ApiResponse(code = 420, message = "Crawler was not running")
    })
    void stop();

    @GetMapping("/running")
    @ApiModelProperty(notes = "True if crawler is running, false otherwise")
    IsRunningResponse isRunning();
}
