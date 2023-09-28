package com.osearch.search.fixture;

import com.osearch.search.application.entity.Pageable;
import com.osearch.search.domain.entity.IndexInfo;

import java.util.List;

public class IndexUseCaseFixture {
    public static final int OFFSET = 0;
    public static final int LIMIT = 2;
    public static final int LIMIT_PLUS_1 = 3;

    public static final String TOPIC_1 = "Work";
    public static final IndexInfo INDEX_INFO_1 = IndexInfo.builder()
        .index(TOPIC_1)
        .pagesCount(2)
        .build();

    public static final String TOPIC_2 = "Code";
    public static final IndexInfo INDEX_INFO_2 = IndexInfo.builder()
        .index(TOPIC_2)
        .pagesCount(2)
        .build();

    public static final String TOPIC_3 = "Video";
    public static final IndexInfo INDEX_INFO_3 = IndexInfo.builder()
        .index(TOPIC_3)
        .pagesCount(2)
        .build();

    public static final List<IndexInfo> INFO =
        List.of(INDEX_INFO_1, INDEX_INFO_2);

    public static final List<IndexInfo> INFO_PLUS_1 =
        List.of(INDEX_INFO_1, INDEX_INFO_2, INDEX_INFO_3);

    public static final Pageable<IndexInfo> PAGEABLE_INFO = Pageable.of(INFO, true);
}
