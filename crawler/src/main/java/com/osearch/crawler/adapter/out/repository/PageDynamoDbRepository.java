package com.osearch.crawler.adapter.out.repository;

import com.osearch.crawler.adapter.out.repository.mapper.PageDynamoDbMapper;
import com.osearch.crawler.adapter.out.repository.properties.DynamoDbRepositoryProperties;
import com.osearch.crawler.application.port.PageRepository;
import com.osearch.crawler.application.port.exception.DataAccessException;
import com.osearch.crawler.domain.entity.Page;

import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.Select;

@RequiredArgsConstructor
public class PageDynamoDbRepository implements PageRepository {
    private final DynamoDbClient client;
    private final PageDynamoDbMapper mapper;
    private final DynamoDbRepositoryProperties properties;

    @Override
    public Optional<Page> findByUrlHash(String hash) {
        try {
            var request = GetItemRequest.builder()
                .tableName(properties.getTableName())
                .key(Map.of("urlHash", AttributeValue.builder().s(hash).build()))
                .consistentRead(true)
                .build();

            var response = client.getItem(request);
            return mapper.toPage(response);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public void save(Page page) {
        try {
            var request = mapper.toRequest(page, properties.getTableName());
            client.putItem(request);
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public long count() {
        try {
            var request = ScanRequest.builder()
                .tableName(properties.getTableName())
                .select(Select.COUNT)
                .build();

            var response = client.scan(request);
            return response.count();
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }
}
