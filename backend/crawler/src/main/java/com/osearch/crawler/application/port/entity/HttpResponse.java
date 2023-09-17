package com.osearch.crawler.application.port.entity;

import java.time.Duration;

public interface HttpResponse {

    String getUrl();
    String getContent();
    Duration getLoadTime();
}
