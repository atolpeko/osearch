package com.osearch.crawler.config;

import com.osearch.crawler.config.properties.KafkaProperties;

import java.util.HashMap;

import lombok.RequiredArgsConstructor;

import org.apache.kafka.clients.producer.ProducerConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;

@Configuration
@RequiredArgsConstructor
@Profile("test")
public class EmbeddedKafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public EmbeddedKafkaBroker embeddedKafkaBroker() {
        return new EmbeddedKafkaBroker(1, true, kafkaProperties.getUrlTopic(),
                kafkaProperties.getRequestTopic(), kafkaProperties.getResponseTopic());
    }

    @Bean
    public ProducerFactory<String, String> producerFactory(EmbeddedKafkaBroker embeddedKafkaBroker) {
        var config = new HashMap<String, Object>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                embeddedKafkaBroker.getBrokersAsString());
        return new DefaultKafkaProducerFactory<>(config);
    }
}
