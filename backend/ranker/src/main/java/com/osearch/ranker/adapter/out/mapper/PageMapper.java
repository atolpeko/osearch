package com.osearch.ranker.adapter.out.mapper;

import com.osearch.ranker.adapter.out.entity.PageDto;
import com.osearch.ranker.domain.entity.Page;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PageMapper {

    @Mapping(source = "totalRank", target = "rank")
    @Mapping(target = "id", ignore = true)
    PageDto toDto(Page page);

    @Mapping(source = "rank", target = "totalRank")
    @Mapping(target = "title", ignore = true)
    @Mapping(target = "loadTime", ignore = true)
    @Mapping(target = "referredPages", ignore = true)
    @Mapping(target = "keywords", ignore = true)
    @Mapping(target = "isIndexed", ignore = true)
    @Mapping(target = "pageRank", ignore = true)
    @Mapping(target = "keywordRank", ignore = true)
    @Mapping(target = "metaRank", ignore = true)
    Page toEntity(PageDto dto);
}
