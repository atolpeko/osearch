package com.osearch.ranker.application.port;

import com.osearch.ranker.domain.entity.Index;
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
}
