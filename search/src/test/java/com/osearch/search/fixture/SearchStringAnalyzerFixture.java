package com.osearch.search.fixture;

import com.osearch.search.domain.valueobject.Topics;
import java.util.List;

public class SearchStringAnalyzerFixture {
    public static final String SEARCH_STRING = "java course online";
    public static final Topics TOPICS = Topics.builder()
        .mainTopic("java course online")
        .sideTopics(List.of("course online", "java course",
            "java online", "course", "online", "java"))
        .build();
}
