package com.osearch.indexer.adapter.out.messaging.mapper;

import com.osearch.indexer.adapter.out.messaging.entity.PageDto;
import com.osearch.indexer.domain.entity.Page;

import org.mapstruct.Mapper;

/**
 * A mapper for converting Page objects to PageDto objects.
 */
@Mapper(componentModel = "spring")
public interface PageMapper {

    /**
     * Converts a Page object to a PageDto object.
     *
     * @param page the Page object to be converted.
     * @return the converted PageDto object.
     */
    PageDto toDto(Page page);
}
