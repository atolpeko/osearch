package com.osearch.search.adapter.in.mapper;

import com.osearch.search.adapter.in.entity.PageResponse;
import com.osearch.search.domain.entity.Page;

import org.mapstruct.Mapper;

/**
 * A mapper for converting Page objects to PageResponse objects.
 */
@Mapper(componentModel = "spring")
public interface PageMapper {

    /**
     * Converts a Page object to a PageResponse object.
     *
     * @param page the Page object to be converted.
     * @return the converted PageResponse object.
     */
    PageResponse toResponse(Page page);
}
