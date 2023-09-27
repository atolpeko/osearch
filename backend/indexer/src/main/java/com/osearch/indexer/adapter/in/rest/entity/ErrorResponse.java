package com.osearch.indexer.adapter.in.rest.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Builder;
import lombok.Data;

/**
 * The ErrorResponse class represents details about any error response from the service.
 */
@Data
@Builder
@ApiModel(
    value = "ErrorResponse",
    description = "Details about any error response from the service"
)
public class ErrorResponse {

    @ApiModelProperty(
        value = "The timestamp at which the error occurred",
        example = "1633698309"
    )
    private final long timestamp;

    @ApiModelProperty(
        value = "The HTTP status code of the error",
        example = "400"
    )
    private final int status;

    @ApiModelProperty(
        value = "Description of the error",
        example = "Bad Request"
    )
    private final String error;

    @ApiModelProperty(
        value = "The endpoint at which the error occurred",
        example = "/api/crawler/start"
    )
    private final String path;
}
