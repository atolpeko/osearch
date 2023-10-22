package com.osearch.crawler.boot.config.external;

import com.osearch.crawler.boot.config.properties.KafkaProperties;

import java.util.HashMap;

import lombok.RequiredArgsConstructor;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@RequiredArgsConstructor
@Profile({"debug", "local", "k8s"})
public class KafkaConfig {

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(
        KafkaProperties kafkaProperties
    ) {
        return new KafkaTemplate<>(producerFactory(kafkaProperties));
    }

    @Bean
    public ProducerFactory<String, String> producerFactory(
        KafkaProperties kafkaProperties
    ) {
        var configProps = new HashMap<String, Object>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getUrl());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG,
            kafkaProperties.getMaxMessageSize());

        return new DefaultKafkaProducerFactory<>(configProps);
    }
}
