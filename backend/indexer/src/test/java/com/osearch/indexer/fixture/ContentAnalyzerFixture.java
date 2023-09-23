package com.osearch.indexer.fixture;

import com.osearch.indexer.domain.entity.AnalyzerContext;
import com.osearch.indexer.domain.entity.Topic;
import com.osearch.indexer.domain.valueobject.Significance;

import java.util.HashSet;
import java.util.Set;

public class ContentAnalyzerFixture {
    public static final String ID = "1";
    public static final String TEXT = "Steve said \"Life is beautiful!\"."
        + " He is right. ";
    public static final String NEW_TEXT = "Steve said \"Life is beautiful!\"."
        + " Steve is right . ";
    public static final String QUOTE = "\"Life is beautiful!\"";

    public static final AnalyzerContext CONTEXT =
        AnalyzerContext.builder()
            .id(ID)
            .content(TEXT)
            .build();

    public static final Set<Topic> TOPICS = Set.of(
        Topic.builder()
            .mainSubject(QUOTE)
            .significance(Significance.of(66.66666666666667))
            .build(),

        Topic.builder()
            .mainSubject("Steve")
            .mainAction("said")
            .significance(Significance.of(66.66666666666667))
            .build(),

        Topic.builder()
            .mainSubject("Steve")
            .mainAction("right")
            .significance(Significance.of(66.66666666666667))
            .build(),

        Topic.builder()
            .mainSubject("Steve")
            .significance(Significance.of(66.66666666666667))
            .build(),

        Topic.builder()
            .mainSubject("Life")
            .significance(Significance.of(33.333333333333336))
            .build(),

        Topic.builder()
            .mainSubject("Life")
            .mainAction("beautiful")
            .significance(Significance.of(33.333333333333336))
            .build()
    );

    public static final AnalyzerContext NEW_CONTEXT =
        AnalyzerContext.builder()
            .id(ID)
            .content(NEW_TEXT)
            .topics(new HashSet<>(TOPICS))
            .build();
}
