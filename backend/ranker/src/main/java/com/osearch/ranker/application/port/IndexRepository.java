package com.osearch.ranker.application.port;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Page;

import java.util.Optional;

/**
 * Used to work with stored indexes.
 */
public interface IndexRepository {

    /**
     * Look for index with the specified keyword.
     *
     * @param keyword  keyword to look for
     *
     * @return found index if it exists or Optional.empty() otherwise
     */
    Optional<Index> findByKeyword(String keyword);

    /**
     * Save index.
     *
     * @param index  index to save
     */
    void save(Index index);

    /**
     * Retrieves the page specified by the index and page URL.
     *
     * @param index    the index to retrieve the page from
     * @param pageUrl  the URL of the page to retrieve
     *
     * @return an optional containing the retrieved page if it exists, otherwise an empty optional
     */
    Optional<Page> getPage(String index, String pageUrl);
}
