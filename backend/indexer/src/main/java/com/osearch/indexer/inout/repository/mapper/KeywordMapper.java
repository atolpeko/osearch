package com.osearch.indexer.inout.repository.mapper;

import com.osearch.indexer.inout.repository.dto.KeywordDto;
import com.osearch.indexer.service.entity.Keyword;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface KeywordMapper {

    @Mapping(target = "id", ignore = true)
    KeywordDto toDto(Keyword keyword);
}
