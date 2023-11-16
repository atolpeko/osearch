package com.osearch.crawler.adapter.out.repository.properties;

/**
 * Represents the properties of a DynamoDB repository.
 */
public interface DynamoDbRepositoryProperties {

    /**
     * Retrieves the name of the table.
     *
     * @return the name of the table
     */
    String getTableName();
}
