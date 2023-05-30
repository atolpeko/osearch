package com.osearch.indexer.inout.messaging.mapper;

import com.osearch.indexer.inout.messaging.entity.NewUrlRequest;
import com.osearch.indexer.service.entity.IndexRequest;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IndexRequestMapper {

    IndexRequest toServiceRequest(NewUrlRequest request);
}
