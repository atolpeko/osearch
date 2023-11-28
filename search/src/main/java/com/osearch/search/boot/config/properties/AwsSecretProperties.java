package com.osearch.search.boot.config.properties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Getter
@Component
@Profile("aws")
public class AwsSecretProperties {

    @Value("${aws.secretName}")
    private String name;

    @Value("${aws.region}")
    private String region;
}
