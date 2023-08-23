package com.osearch.crawler.adapter.in.rest.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

@Data
@ApiModel(description = "The number of crawled pages")
public class PagesCountResponse {

    @ApiModelProperty(value = "The number of crawled pages", example = "1000")
    private final Long count;
}
