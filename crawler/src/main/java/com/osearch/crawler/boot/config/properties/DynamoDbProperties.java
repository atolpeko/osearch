package com.osearch.crawler.boot.config.properties;

import com.osearch.crawler.adapter.out.repository.properties.DynamoDbRepositoryProperties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("aws")
@Component(value = "dynamoDbProperties")
public class DynamoDbProperties implements DynamoDbRepositoryProperties {

    @Value("${dynamodb.tableName}")
    private String table;

    @Override
    public String getTableName() {
        return table;
    }
}
