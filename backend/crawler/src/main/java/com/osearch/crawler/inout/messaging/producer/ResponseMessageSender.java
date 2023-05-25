package com.osearch.crawler.inout.messaging.producer;

import com.osearch.crawler.inout.messaging.entity.Response;

public interface ResponseMessageSender {

    void send(Response response);
}
