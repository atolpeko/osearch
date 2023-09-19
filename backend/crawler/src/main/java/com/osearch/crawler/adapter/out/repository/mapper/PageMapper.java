package com.osearch.crawler.adapter.out.repository.mapper;

import com.osearch.crawler.adapter.out.repository.dto.PageDto;
import com.osearch.crawler.domain.entity.Page;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * The PageMapper interface is responsible for mapping between Page and PageDto objects.
 */
@Mapper(componentModel = "spring", implementationName = "RepositoryPageMapper")
public interface PageMapper {

    /**
     * Converts a Page object to a PageDto object.
     *
     * @param page the Page object to convert.
     * @return the converted PageDto object.
     */
    @Mapping(target = "id", ignore = true)
    PageDto toDto(Page page);

    /**
     * Converts a PageDto object to a Page object.
     *
     * @param pageDto the PageDto object to convert.
     * @return the converted Page object.
     */
    @Mapping(target = "url", ignore = true)
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "loadTime", ignore = true)
    @Mapping(target = "nestedUrls", ignore = true)
    Page toEntity(PageDto pageDto);
}
