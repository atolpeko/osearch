package com.osearch.ranker.fixture;

import com.osearch.ranker.adapter.in.entity.Request;

public class NewPageListenerFixture {
    public static final String TOPIC = "TOPIC";
    public static final long ID = 1;
    public static final Request REQUEST = new Request(ID);
    public static final String REQUEST_JSON = "{ \"id\":\"1\" }";
    public static final String INVALID_REQUEST_JSON = "{  }";
}
