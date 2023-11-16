package com.osearch.crawler.fixture;

import com.osearch.crawler.domain.entity.Page;

import java.util.Map;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanResponse;
import software.amazon.awssdk.services.dynamodb.model.Select;

public class PageDynamoDbRepositoryFixture {
    public static final String TABLE_NAME = "pages";
    public static final int PAGES_NUMBER = 5;

    public static final String PAGE_HASH = "300d41ba4d0f3468bf95e319db2c0a85";
    public static final String URL_HASH = "304c42801cd263892249f4d473f4ee72";

    public static final Page PAGE =
        Page.builder()
            .urlHash(URL_HASH)
            .hash(PAGE_HASH)
            .build();

    public static final GetItemResponse PAGE_RESPONSE =
        GetItemResponse.builder()
            .item(Map.of(
                "hash", AttributeValue.builder().s(PAGE_HASH).build(),
                "urlHash", AttributeValue.builder().s(URL_HASH).build()
            ))
            .build();

    public static final GetItemResponse EMPTY_PAGE_RESPONSE =
        GetItemResponse.builder().build();

    public static final ScanRequest COUNT_REQUEST =
        ScanRequest.builder()
            .tableName(TABLE_NAME)
            .select(Select.COUNT)
            .build();

    public static final ScanResponse COUNT_RESPONSE =
        ScanResponse.builder()
            .count(PAGES_NUMBER)
            .build();

    public static GetItemRequest pageRequest(String urlHash) {
        return GetItemRequest.builder()
            .tableName(TABLE_NAME)
            .key(Map.of("urlHash", AttributeValue.builder().s(urlHash).build()))
            .consistentRead(true)
            .build();
    }

    public static PutItemRequest putPageRequest() {
        var item = Map.of(
            "urlHash", AttributeValue.builder().s(URL_HASH).build(),
            "hash", AttributeValue.builder().s(PAGE_HASH).build()
        );

        return PutItemRequest.builder()
            .tableName(TABLE_NAME)
            .item(item)
            .build();
    }
}
