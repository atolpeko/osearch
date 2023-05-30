package com.osearch.indexer.service.processor;

/**
 * Index processor
 */
public interface Processor extends Runnable {

    /**
     * Starts an infinite loop processing IndexRequests.
     */
    void run();
}
