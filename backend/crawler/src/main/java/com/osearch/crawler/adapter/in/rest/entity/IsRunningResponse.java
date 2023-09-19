package com.osearch.crawler.adapter.in.rest.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

/**
 * Represents the response object that contains information about
 * the running state of the crawler service.
 */
@Data
@ApiModel(
    value = "IsRunningResponse",
    description = "Details about the running state of the crawler service"
)
public class IsRunningResponse {

    @ApiModelProperty(
        value = "An indicator representing whether the crawler service is running or not.",
        example = "true"
    )
    private final Boolean isRunning;
}
