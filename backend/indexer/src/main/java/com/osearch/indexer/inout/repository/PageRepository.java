package com.osearch.indexer.inout.repository;

import com.osearch.indexer.service.entity.Page;

/**
 * Used to work with PageDto objects stored in neo.
 */
public interface PageRepository {

    Long save(Page pageDto);
}
