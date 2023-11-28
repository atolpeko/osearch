package com.osearch.ranker.boot.config.external.secret;

import lombok.Data;

@Data
public class AwsDbSecret {
    private String host;
    private String port;
    private String username;
    private String password;
    private String dbName;
    private String engine;
}
