package com.osearch.indexer.fixture;

import com.osearch.indexer.adapter.out.messaging.entity.PageDto;
import com.osearch.indexer.domain.entity.Page;

public class PageMessageSenderFixture {
    public static final String TOPIC = "TOPIC";
    public static final long ID = 343242943L;
    public static final String JSON = "{\"id\":\"" + ID + "\"}";

    public static final Page PAGE =
        Page.builder()
            .id(ID)
            .build();

    public static final PageDto PAGE_DTO = new PageDto(ID);
}
