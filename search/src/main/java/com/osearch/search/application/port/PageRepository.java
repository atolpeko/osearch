package com.osearch.search.application.port;

import com.osearch.search.application.port.exception.DataAccessException;
import com.osearch.search.domain.entity.Page;

import java.util.List;

/**
 * The PageRepository interface provides methods to interact
 * with a repository of Page objects.
 */
public interface PageRepository {

    /**
     * Retrieves a list of pages by topic with pagination.
     *
     * @param topic  the topic to filter pages by
     * @param offset the number of pages to skip
     * @param limit  the maximum number of pages to retrieve
     *
     * @return a list of pages matching the given topic
     *
     * @throws DataAccessException if any repository error happens
     */
    List<Page> findAllByTopic(String topic, int offset, int limit);

    /**
     * Retrieves the number of pages for a given topic.
     *
     * @param topic the topic to filter pages by
     * @return the number of pages matching the given topic
     *
     * @throws DataAccessException if any repository error happens
     */
    int countForTopic(String topic);
}
