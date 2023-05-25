package com.osearch.crawler.inout.web.entity;

import com.osearch.crawler.util.validation.ValidURLList;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@ApiModel(description = "Crawler start request entity")
public class StartRequest {

    @ApiModelProperty(
            value = "Initial URLs to look for",
            example = "[https://en.wikipedia.org/wiki/Wikipedia, https://www.reddit.com]"
    )
    @NotEmpty(message = "List of URLs should not be empty")
    @ValidURLList
    private List<String> urls;
}
