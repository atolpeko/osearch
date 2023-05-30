package com.osearch.indexer.fixture;

import com.osearch.indexer.inout.messaging.entity.IndexChange;

public class IndexChangedMessageSenderFixture {
    public static final long ID = 1L;
    public static final String TOPIC = "TOPIC";

    public static IndexChange request() {
        return new IndexChange(ID);
    }

    public static String message() {
        return "{ \"pageId\": \"" + ID + "\" }";
    }
}
