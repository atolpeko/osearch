package com.osearch.ranker.boot.config;

import com.osearch.ranker.boot.config.properties.KafkaProperties;

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
@Profile({"debug", "local", "prod"})
public class KafkaConfig {

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate(
        KafkaProperties kafkaProperties
    ) {
        return new KafkaTemplate<>(producerFactory(kafkaProperties));
    }

    @Bean
    public ProducerFactory<String, String> producerFactory(
        KafkaProperties properties
    ) {
        var configProps = new HashMap<String, Object>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getUrl());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps);
    }
}
