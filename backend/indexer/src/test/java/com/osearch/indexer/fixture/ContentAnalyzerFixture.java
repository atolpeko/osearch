package com.osearch.indexer.fixture;

import com.osearch.indexer.service.entity.IndexRequest;
import com.osearch.indexer.service.entity.Keyword;
import com.osearch.indexer.service.entity.Page;
import com.osearch.indexer.service.entity.StopWords;

import java.time.Duration;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ContentAnalyzerFixture {
    public static final String URL = "https://host/resource";
    public static final long LOAD_TIME = 1000L;

    public static final String CONTENT =
            "<!doctype html>\n" +
                "<html lang=\"en\">\n " +
                    "<head> " +
                        "<meta charset=\"UTF-8'\"> " +
                        "<meta name=\"description\" content=\"A travel blog sharing adventures around the world.\">" +
                        "<meta name=\"keywords\" content=\"travel, adventure, blog, world, destinations\">" +
                        "<meta name=\"author\" content=\"John Doe\">" +
                        "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                        "<title>My Travel Blog</title>" +
                    "</head>\n " +
                    "<body>\n " +
                        "<h1>Hello! This is a travel blog. Today I'm gonna show you Spain</h1>\n " +
                        "<p>Holiday vibes are a way of life in beautiful Spain!</p> " +
                    "</body>\n " +
                "</html>";


    public static IndexRequest request() {
        return IndexRequest.builder()
                .url(URL)
                .content(CONTENT)
                .loadTime(LOAD_TIME)
                .build();
    }

    public static Page page() {
        var keywords = new HashSet<>(
                Set.of(
                        new Keyword("vibes",  1),
                        new Keyword("sharing",  1),
                        new Keyword("spain",  2),
                        new Keyword("hello",  1),
                        new Keyword("travel",  4),
                        new Keyword("adventure",  1),
                        new Keyword("adventures",  1),
                        new Keyword("holiday",  1),
                        new Keyword("around",  1),
                        new Keyword("life",  1),
                        new Keyword("show",  1),
                        new Keyword("beautiful",  1),
                        new Keyword("blog",  4),
                        new Keyword("world",  2),
                        new Keyword("today",  1),
                        new Keyword("destinations",  1)
                )
        );

        return Page.builder()
                .url(URL)
                .title("My Travel Blog")
                .keywords(keywords)
                .loadTime(Duration.ofMillis(LOAD_TIME))
                .build();
    }

    public static StopWords stopWordsForEng() {
        var localeToWords = Map.of(
                Locale.ENGLISH,
                Set.of("a" ,"about",  "after",  "all",  "also",  "am",  "an",  "and",  "another",
                        "any",  "are", "as",  "at",  "be",  "because",  "been",  "before",  "being",
                        "between",  "both",  "but",  "by", "came",  "can",  "come",  "could",  "did",
                        "do",  "each",  "for",  "from",  "get",  "got", "gonna", "has", "had",  "he",
                        "have",  "her",  "here",  "him",  "himself",  "his",  "how",  "if",  "in",  "into",
                        "is",  "it",  "like",  "make",  "many",  "me",  "might",  "more",  "most",  "much",
                        "must",  "my", "never",  "now",  "of",  "on",  "only",  "or",  "other",  "our",
                        "out",  "over",  "said",  "same", "should",  "since",  "some",  "still",  "such",
                        "take",  "than",  "that",  "the",  "their",  "them", "then",  "there",  "these",
                        "they", "they're",  "this",  "those",  "through",  "to",  "too", "under",
                        "up", "very",  "was",  "way", "wanna", "we", "we're",  "well",  "were",
                        "what", "where",  "which", "while",  "who",  "with", "would",  "you",
                        "your", "you're",  "i", "i'm")
        );

        return new StopWords(localeToWords);
    }
}
