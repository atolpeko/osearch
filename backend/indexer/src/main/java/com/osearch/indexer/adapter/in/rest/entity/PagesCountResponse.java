package com.osearch.indexer.adapter.in.rest.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

/**
 * Represents the response containing information about the quantity of indexed pages.
 */
@Data
@ApiModel(
    value = "PagesCountResponse",
    description = "Information about the quantity of indexed pages"
)
public class PagesCountResponse {

    @ApiModelProperty(
        value = "Number denoting the total number of pages that have been indexed",
        example = "1000",
        dataType = "Integer")
    private final Integer count;
}
