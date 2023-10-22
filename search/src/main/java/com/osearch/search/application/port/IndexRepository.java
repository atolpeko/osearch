package com.osearch.search.application.port;

import com.osearch.search.application.port.exception.DataAccessException;
import com.osearch.search.domain.entity.IndexInfo;

import java.util.List;

/**
 * IndexRepository is an interface that provides methods for
 * retrieving indexes information with pagination.
 */
public interface IndexRepository {

    /**
     * Finds a list of index information with pagination.
     *
     * @param offset the starting index of the search
     * @param limit the maximum number of results to return
     *
     * @return a list of IndexesInfo objects
     *
     * @throws DataAccessException if any repository error happens
     */
    List<IndexInfo> findInfo(int offset, int limit);
}
