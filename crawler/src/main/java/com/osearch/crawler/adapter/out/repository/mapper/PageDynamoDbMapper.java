package com.osearch.crawler.adapter.out.repository.mapper;

import com.osearch.crawler.domain.entity.Page;

import java.util.Map;
import java.util.Optional;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

/**
 * The PageMapper interface is responsible for mapping between
 * DynamoDB response objects and Page objects.
 */
public class PageDynamoDbMapper {

    /**
     * Converts a GetItemResponse into a Page object.
     *
     * @param response the GetItemResponse object to be converted

     * @return a Page object representing the converted GetItemResponse or
     * Optional.empty() if the page is not present
     */
    public Optional<Page> toPage(GetItemResponse response) {
        if (!response.hasItem()) {
            return Optional.empty();
        }

        var urlHash = Optional.ofNullable(response.item().get("urlHash"))
            .orElse(AttributeValue.builder().s("").build())
            .s();
        var pageHash = Optional.ofNullable(response.item().get("hash"))
            .orElse(AttributeValue.builder().s("").build())
            .s();

        var page = Page.builder()
            .urlHash(urlHash)
            .hash(pageHash)
            .build();
        return Optional.of(page);
    }

    /**
     * Converts a Page object into a PutItemRequest.
     *
     * @param page the Page object to be converted
     * @return a PutItemRequest object representing the converted Page
     */
    public PutItemRequest toRequest(Page page, String tableName) {
        var item = Map.of(
            "urlHash", AttributeValue.builder().s(page.getUrlHash()).build(),
            "hash", AttributeValue.builder().s(page.getHash()).build()
        );

        return PutItemRequest.builder()
            .tableName(tableName)
            .item(item)
            .build();
    }
}
