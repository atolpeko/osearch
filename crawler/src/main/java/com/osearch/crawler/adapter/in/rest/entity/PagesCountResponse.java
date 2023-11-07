package com.osearch.crawler.adapter.in.rest.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

/**
 * Represents the response containing information about the quantity of crawled pages.
 */
@Data
@ApiModel(
    value = "PagesCountResponse",
    description = "Information about the quantity of crawled pages"
)
public class PagesCountResponse {

    @ApiModelProperty(
        value = "Number denoting the total number of pages that have been crawled",
        example = "1000",
        dataType = "Long")
    private final Long count;
}
