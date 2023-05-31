package com.osearch.indexer.inout.repository.mapper;

import com.osearch.indexer.inout.repository.dto.PageDto;
import com.osearch.indexer.service.entity.Page;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isIndexed", ignore = true)
    @Mapping(target = "keywordRelations", ignore = true)
    PageDto toDto(Page page);
}
