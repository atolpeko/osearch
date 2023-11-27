package com.osearch.indexer.adapter.out.repository;

import org.neo4j.driver.Driver;

/**
 * Implements the PageRepository that works with a Neo4J database.
 */
public class PageNeo4jRepository extends PageGraphRepository {

    /**
     * Constructs a new PageNeo4jRepository with the provided bolt driver.
     *
     * @param driver the driver used for connecting to the database
     */
    public PageNeo4jRepository(Driver driver) {
        super(driver, driver);
    }
}
