package com.osearch.crawler.inout.web.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

@Data
@ApiModel(description = "The number of crawled URLs")
public class UrlsCountResponse {

    @ApiModelProperty(value = "The number of crawled URLs", example = "1000")
    private final Long count;
}
