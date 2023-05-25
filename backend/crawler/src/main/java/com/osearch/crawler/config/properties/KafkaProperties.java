package com.osearch.crawler.config.properties;

import lombok.Getter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kafka")
public class KafkaProperties {

    @Getter
    private static String url;

    @Getter
    private static String groupId;

    @Getter
    private static String urlTopic;

    @Getter
    private static String requestTopic;

    @Getter
    private static String responseTopic;

    @Getter
    private static Integer bulkMessagesCount;

    void setUrl(String url) {
        KafkaProperties.url = url;
    }

    void setGroupId(String groupId) {
        KafkaProperties.groupId = groupId;
    }

    void setUrlTopic(String urlTopic) {
        KafkaProperties.urlTopic = urlTopic;
    }

    void setRequestTopic(String requestTopic) {
        KafkaProperties.requestTopic = requestTopic;
    }

    void setResponseTopic(String responseTopic) {
        KafkaProperties.responseTopic = responseTopic;
    }

    void setBulkMessagesCount(Integer bulkMessagesCount) {
        KafkaProperties.bulkMessagesCount = bulkMessagesCount;
    }
}
