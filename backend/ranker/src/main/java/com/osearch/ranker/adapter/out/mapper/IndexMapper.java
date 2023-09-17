package com.osearch.ranker.adapter.out.mapper;

import com.osearch.ranker.adapter.out.entity.IndexDto;
import com.osearch.ranker.domain.entity.Index;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = PageMapper.class, componentModel = "spring")
public interface IndexMapper {

    @Mapping(target = "id", ignore = true)
    IndexDto toDto(Index index);

    Index toEntity(IndexDto dto);
}
