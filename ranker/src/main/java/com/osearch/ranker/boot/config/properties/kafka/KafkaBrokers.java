package com.osearch.ranker.boot.config.properties.kafka;

import java.util.List;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka")
public class KafkaBrokers {
    private List<String> brokers;
}
