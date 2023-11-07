package com.osearch.search.adapter.in.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

/**
 * Represents the response received when requesting for indexes info.
 */
@Data
@Builder
@ApiModel(
    value = "IndexInfoResponse",
    description = "Model representing the response received "
        + "when requesting for indexes info."
)
public class IndexInfoResponse {

    @ApiModelProperty(
        value = "A map of existing indexes to the number of pages for each index",
        example = "{ "
            + "\"Java\": 908, "
            + "\"C++\": 718, "
            + "\"Python\": 1030 }",
        dataType = "Map<String, Integer>"
    )
    private Map<String, Integer> indexToPagesCount;

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
