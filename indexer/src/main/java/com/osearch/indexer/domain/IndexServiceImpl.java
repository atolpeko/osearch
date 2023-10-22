package com.osearch.indexer.domain;

import com.osearch.indexer.domain.analyzer.ContentAnalyzer;
import com.osearch.indexer.domain.valueobject.IndexRequest;
import com.osearch.indexer.domain.entity.Page;
import com.osearch.indexer.domain.valueobject.AnalyzerContext;
import com.osearch.indexer.domain.entity.Topic;
import com.osearch.indexer.domain.exception.UnsupportedLocaleException;
import com.osearch.indexer.domain.valueobject.Significance;
import com.osearch.indexer.domain.valueobject.SupportedLocales;

import java.time.Duration;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Log4j2
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {
    private final ContentAnalyzer contentAnalyzer;
    private final SupportedLocales supportedLocales;

    @Override
    public Page index(IndexRequest request) {
        log.debug("Indexing {}", request.getUrl());
        var document = Jsoup.parse(request.getContent());
        checkLocale(document);

        var context = AnalyzerContext.builder()
            .id(request.getUrl())
            .content(document.text())
            .build();
        contentAnalyzer.analyze(context);

        return Page.builder()
            .url(request.getUrl())
            .title(document.title())
            .topics(getTopics(context, document.title()))
            .loadTime(Duration.ofMillis(request.getLoadTime()))
            .build();
    }

    private void checkLocale(Document document) {
        var locale = getLocale(document);
        var notSupported = supportedLocales.get().stream()
            .map(Locale::toLanguageTag)
            .map(String::toLowerCase)
            .noneMatch((l -> l.equals(locale.toLanguageTag().toLowerCase())));
        if (locale == null || notSupported) {
            var mg = locale + " is not a supported locale. Locales available: "
                + Arrays.toString(supportedLocales.get().toArray());
            throw new UnsupportedLocaleException(mg);
        }
    }

    private Locale getLocale(Document document) {
        var localeInHtml = Optional.ofNullable(document.selectFirst("html"))
            .map(element -> element.attr("lang"))
            .map(Locale::forLanguageTag);
        if (localeInHtml.isPresent()) {
            return localeInHtml.get();
        }

        var localeInMeta = Optional.ofNullable(document
                .select("meta[name=language], meta[http-equiv=content-language]")
                .first())
            .map(element -> element.attr("content"))
            .map(Locale::forLanguageTag);
        return localeInMeta.orElse(null);
    }

    private Set<Topic> getTopics(AnalyzerContext context, String pageTitle) {
        var title = Topic.builder()
            .mainSubject(pageTitle)
            .significance(Significance.of(100))
            .build();

        var topics = context.getTopics();
        topics.add(title);
        return topics;
    }
}
