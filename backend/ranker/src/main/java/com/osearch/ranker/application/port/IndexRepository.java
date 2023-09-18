package com.osearch.ranker.application.port;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.domain.entity.Page;
import com.osearch.ranker.application.port.exception.DataAccessException;
import com.osearch.ranker.application.port.exception.DataModificationException;

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
     *
     * @throws DataAccessException if any repository exception occurred
     */
    Optional<Index> findByKeyword(String keyword);

    /**
     * Save index.
     *
     * @param index  index to save
     *
     * @throws DataModificationException if save fails
     * @throws DataAccessException       if any other repository exception occurred
     */
    void save(Index index);

    /**
     * Retrieves the page specified by the index and page URL.
     *
     * @param index    the index to retrieve the page from
     * @param pageUrl  the URL of the page to retrieve
     *
     * @return an optional containing the retrieved page if it exists, otherwise an empty optional
     *
     * @throws DataAccessException if any repository exception occurred
     */
    Optional<Page> getPage(String index, String pageUrl);
}
