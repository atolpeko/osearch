package com.osearch.ranker.domain.service.indexer;

import com.osearch.ranker.domain.entity.Keyword;
import com.osearch.ranker.domain.entity.Page;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class IndexImpl implements Indexer {

    @Override
    public Set<String> index(Page page) {
        var keywords = page.getKeywords().stream()
            .sorted(Comparator.comparing(Keyword::getOccurrences))
            .filter(keyword -> keyword.getOccurrences() >= 2)
            .map(Keyword::getValue)
            .collect(Collectors.toList());

        var indexes = new HashSet<String>();
        indexes.add(page.getTitle());
        indexes.addAll(keywords);

        return indexes;
    }
}
