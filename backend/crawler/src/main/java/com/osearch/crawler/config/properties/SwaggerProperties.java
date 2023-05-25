package com.osearch.crawler.config.properties;

import lombok.Getter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    @Getter
    private static String title;

    @Getter
    private static String description;

    @Getter
    private static String version;

    @Getter
    private static String controllersPackage;

    void setTitle(String title) {
        SwaggerProperties.title = title;
    }

    void setDescription(String description) {
        SwaggerProperties.description = description;
    }

    void setVersion(String version) {
        SwaggerProperties.version = version;
    }

    void setControllersPackage(String controllersPackage) {
        SwaggerProperties.controllersPackage = controllersPackage;
    }
}
