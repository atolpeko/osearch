package com.osearch.crawler.adapter.out.repository.mapper;

import com.osearch.crawler.adapter.out.repository.dto.PageDto;
import com.osearch.crawler.domain.entity.Page;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", implementationName = "RepositoryPageMapper")
public interface PageMapper {

    @Mapping(target = "id", ignore = true)
    PageDto toDto(Page page);

    @Mapping(target = "url", ignore = true)
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "loadTime", ignore = true)
    @Mapping(target = "nestedUrls", ignore = true)
    Page toEntity(PageDto pageDto);
}
