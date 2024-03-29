package com.osearch.crawler.adapter.in.rest.entity;

import com.osearch.crawler.adapter.in.rest.validation.ValidURLList;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * The StartRequest class represents a crawler start request entity.
 */
@Data
@ApiModel(
    value = "StartRequest",
    description = "Crawler start request entity"
)
public class StartRequest {

    @ApiModelProperty(
        value = "Initial URLs to look for",
        example = "[ https://www.foxnews.com,"
            + " https://www.bbc.com,"
            + " https://edition.cnn.com,"
            + " https://www.aljazeera.com,"
            + " https://www.goal.com/es,"
            + " https://www.reddit.com ]"
    )
    @NotEmpty(message = "The list of URLs should not be empty")
    @ValidURLList
    private List<String> urls;
}
