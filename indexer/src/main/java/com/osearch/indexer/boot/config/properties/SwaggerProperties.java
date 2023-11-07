package com.osearch.indexer.boot.config.properties;

import com.osearch.indexer.adapter.in.rest.properties.RestProperties;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SwaggerProperties implements RestProperties {

    @Value("${swagger.url}")
    private String swaggerUrl;

    @Getter
    @Value("${swagger.title}")
    private String title;

    @Getter
    @Value("${swagger.description}")
    private String description;

    @Getter
    @Value("${swagger.version}")
    private String version;

    @Getter
    @Value("${swagger.controllersPackage}")
    private String controllersPackage;

    @Override
    public String getSwaggerUrl() {
        return swaggerUrl;
    }
}
