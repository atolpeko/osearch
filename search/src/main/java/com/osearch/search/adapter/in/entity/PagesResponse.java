package com.osearch.search.adapter.in.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * Represents the response received when requesting for pages.
 */
@Data
@Builder
@ApiModel(
    value = "PagesResponse",
    description = "Model representing the response received "
        + "when requesting for pages"
)
public class PagesResponse {

    @ApiModelProperty(
        value = "List of found pages",
        example = "[ "
            + "{ \"url\": \"https://github.com/\", \"title\": \"GitHub\"}, "
            + "{ \"url\": \"https://www.youtube.com/\", \"title\": \"YouTube\"}, "
            + "{ \"url\": \"https://www.linkedin.com/\", \"title\": \"LinkedIn\"} ] ",
        dataType = "List<PagesResponse>"
    )
    private List<PageResponse> pages;

    @ApiModelProperty(
        value = "The offset for the next page of results "
            + "or null if there are no more pages left",
        example = "150",
        dataType = "Integer"
    )
    private Integer nextOffset;

    @ApiModelProperty(
        value = "The last offset used",
        example = "100",
        dataType = "Integer"
    )
    private Integer lastOffset;

    @ApiModelProperty(
        value = "The last limit used",
        example = "50",
        dataType = "Integer"
    )
    private Integer lastLimit;
}
