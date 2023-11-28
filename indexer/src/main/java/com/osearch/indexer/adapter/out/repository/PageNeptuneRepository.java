package com.osearch.indexer.adapter.out.repository;

import org.neo4j.driver.Driver;

/**
 * Implements the PageRepository that works with an AWS Neptune database.
 */
public class PageNeptuneRepository extends PageGraphRepository {

    /**
     * Constructs a new PageNeptuneRepository object with the specified read and write bolt drivers.
     *
     * @param readDriver the driver used for reading data from Neptune
     * @param writeDriver the driver used for writing data to Neptune
     */
    public PageNeptuneRepository(Driver readDriver, Driver writeDriver) {
        super(readDriver, writeDriver);
    }
}
