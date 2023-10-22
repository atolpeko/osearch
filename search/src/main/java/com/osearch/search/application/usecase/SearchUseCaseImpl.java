package com.osearch.search.application.usecase;

import com.osearch.search.application.entity.Pageable;
import com.osearch.search.application.port.PageRepository;
import com.osearch.search.application.port.exception.DataAccessException;
import com.osearch.search.application.usecase.exception.UseCaseException;
import com.osearch.search.domain.analyzer.SearchStringAnalyzer;
import com.osearch.search.domain.entity.Page;
import com.osearch.search.domain.valueobject.Topics;

import java.util.HashMap;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchUseCaseImpl implements SearchUseCase {
    private final SearchStringAnalyzer analyzer;
    private final PageRepository repository;

    @Override
    public Pageable<Page> search(String searchString, int offset, int limit) {
        try {
            var topics = analyzer.analyze(searchString);
            var pages = getPagesForTopics(topics, offset, limit + 1);
            var hasNext = pages.size() == limit + 1;
            if (hasNext) {
                pages = pages.subList(0, pages.size() - 1);
            }

            return Pageable.of(pages, hasNext);
        } catch (DataAccessException e) {
            throw new UseCaseException("DB not available: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new UseCaseException(e.getMessage(), e);
        }
    }

    private List<Page> getPagesForTopics(Topics topics, int offset, int limit) {
        var pagesForTopic = new HashMap<String, List<Page>>();
        String lastTopic = null;
        var pagesFound = 0;

        for (var topic: getAllTopics(topics)) {
            var offsetForTopic = offset;
            if (lastTopic != null) {
                if (!pagesForTopic.get(lastTopic).isEmpty()) {
                    offsetForTopic = 0;
                } else {
                    var previousPages = repository.countForTopic(lastTopic);
                    offsetForTopic = offset - previousPages;
                }
            }

            var limitForTopic = limit;
            if (lastTopic != null) {
                limitForTopic = limit - pagesFound;
            }

            var pages = repository.findAllByTopic(topic, offsetForTopic, limitForTopic);
            pagesForTopic.put(topic, pages);
            lastTopic = topic;
            pagesFound += pages.size();

            if (pagesFound == limit) {
                break;
            }
        }

        return pagesForTopic.values().stream()
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }

    private List<String> getAllTopics(Topics topics) {
        return Stream.of(
            List.of(topics.getMainTopic()),
                topics.getSideTopics())
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }
}
