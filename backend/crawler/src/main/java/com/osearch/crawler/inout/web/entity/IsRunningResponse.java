package com.osearch.crawler.inout.web.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

@Data
@ApiModel(description = "Is crawler service running or not")
public class IsRunningResponse {

    @ApiModelProperty(value = "Is crawler service running or not", example = "true")
    private final Boolean isRunning;
}
