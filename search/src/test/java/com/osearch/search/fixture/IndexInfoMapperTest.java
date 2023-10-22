package com.osearch.search.fixture;

import com.osearch.search.domain.entity.IndexInfo;

import java.util.List;
import java.util.Map;

public class IndexInfoMapperTest {

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

    public static final List<IndexInfo> SOURCE =
        List.of(INDEX_INFO_1, INDEX_INFO_2);

    public static final Map<String, Integer> RESULT =
        Map.of(TOPIC_1, 2, TOPIC_2, 2);
}
