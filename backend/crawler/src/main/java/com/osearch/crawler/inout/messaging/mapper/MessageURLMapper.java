package com.osearch.crawler.inout.messaging.mapper;

import com.osearch.crawler.inout.messaging.entity.URLDto;
import com.osearch.crawler.service.entity.URL;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageURLMapper {

    @Mapping(target = "loadTime", expression = "java(url.getLoadTime().toMillis())")
    @Mapping(target = "url", source = "value")
    URLDto toDto(URL url);
}
