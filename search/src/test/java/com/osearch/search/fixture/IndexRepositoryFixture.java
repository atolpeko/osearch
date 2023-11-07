package com.osearch.search.fixture;

import com.osearch.search.domain.entity.IndexInfo;
import java.util.List;

public class IndexRepositoryFixture {
    public static final int OFFSET = 0;
    public static final int LIMIT = 10;

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

    public static final List<IndexInfo> INFO =
        List.of(INDEX_INFO_1, INDEX_INFO_2);
}
