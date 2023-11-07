package com.osearch.search.adapter.in.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

/**
 * Represents a web page response with information such as URL and title
 */
@Data
@ApiModel(
    value = "PageResponse",
    description = "Information about a web page"
)
public class PageResponse {

    @ApiModelProperty(
        value = "Page URL",
        example = "https://github.com/"
    )
    private String url;

    @ApiModelProperty(
        value = "Page title",
        example = "GitHub"
    )
    private String title;
}
