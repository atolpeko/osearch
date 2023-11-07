package com.osearch.crawler.adapter.out.messaging.mapper;

import com.osearch.crawler.adapter.out.messaging.entity.PageDto;
import com.osearch.crawler.domain.entity.Page;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * A mapper for converting Page objects to PageDto objects.
 */
@Mapper(componentModel = "spring", implementationName = "MessagingPageMapper")
public interface PageMapper {

    /**
     * Converts a Page object to a PageDto object.
     *
     * @param page the Page object to be converted.
     * @return the converted PageDto object.
     */
    @Mapping(target = "loadTime", expression = "java(page.getLoadTime().toMillis())")
    PageDto toDto(Page page);
}
