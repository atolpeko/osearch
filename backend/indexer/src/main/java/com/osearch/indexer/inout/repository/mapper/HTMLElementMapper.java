package com.osearch.indexer.inout.repository.mapper;

import com.osearch.indexer.inout.repository.dto.HTMLElementDto;
import com.osearch.indexer.service.entity.HTMLElement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HTMLElementMapper {

    @Mapping(target = "id", ignore = true)
    HTMLElementDto toDto(HTMLElement element);
}
