package com.osearch.crawler.inout.repository.mapper;

import com.osearch.crawler.inout.repository.dto.URLDto;
import com.osearch.crawler.service.entity.URL;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RepositoryURLMapper {

    @Mapping(target = "id", ignore = true)
    URLDto toDto(URL url);

    @Mapping(target = "value", ignore = true)
    @Mapping(target = "content", ignore = true)
    @Mapping(target = "loadTime", ignore = true)
    @Mapping(target = "nestedUrls", ignore = true)
    URL toEntity(URLDto urlDto);
}
