package com.osearch.crawler.inout.messaging.mapper;

import com.osearch.crawler.inout.messaging.entity.URLDto;
import com.osearch.crawler.service.entity.URL;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageURLMapper {

    URLDto toDto(URL url);

    @Mapping(target = "urlHash", ignore = true)
    @Mapping(target = "pageHash", ignore = true)
    @Mapping(target = "nestedUrls", ignore = true)
    URL toEntity(URLDto urlDto);
}
