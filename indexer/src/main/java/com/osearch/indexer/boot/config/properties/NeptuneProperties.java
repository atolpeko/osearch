package com.osearch.indexer.boot.config.properties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Getter
@Component
@Profile("aws")
public class NeptuneProperties {

    @Value("${neptune.readEndpoint}")
    private String readEndpoint;

    @Value("${neptune.writeEndpoint}")
    private String writeEndpoint;
}
