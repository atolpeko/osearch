package com.osearch.crawler.adapter.out.messaging.mapper;

import com.osearch.crawler.adapter.out.messaging.entity.PageDto;
import com.osearch.crawler.domain.entity.Page;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", implementationName = "MessagingPageMapper")
public interface PageMapper {

    @Mapping(target = "loadTime", expression = "java(page.getLoadTime().toMillis())")
    PageDto toDto(Page page);
}
