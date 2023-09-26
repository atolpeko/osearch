package com.osearch.ranker.application.port;

import com.osearch.ranker.domain.entity.Index;
import com.osearch.ranker.application.port.exception.DataAccessException;
import com.osearch.ranker.application.port.exception.DataModificationException;

import java.util.Optional;

/**
 * Represents a repository for managing indexes.
 */
public interface IndexRepository {

    /**
     * Look for the index with the specified topic.
     *
     * @param topic  the topic to look for.
     *
     * @return found index if it exists or Optional.empty() otherwise.
     *
     * @throws DataAccessException if any repository exception occurred.
     */
    Optional<Index> findByTopic(String topic);

    /**
     * Save the index.
     *
     * @param index  the index to save.
     *
     * @throws DataModificationException  if save fails.
     * @throws DataAccessException        if any other repository exception occurred.
     */
    void save(Index index);
}
