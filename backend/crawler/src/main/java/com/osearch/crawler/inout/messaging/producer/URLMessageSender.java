package com.osearch.crawler.inout.messaging.producer;

import com.osearch.crawler.inout.messaging.entity.URLDto;

public interface URLMessageSender {

    void send(URLDto dto);
}
