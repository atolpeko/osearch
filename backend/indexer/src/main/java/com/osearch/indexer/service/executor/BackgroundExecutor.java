package com.osearch.indexer.service.executor;

import java.util.Collection;

/**
 * Used to execute a set of task on the background without waiting for them to finish.
 */
public interface BackgroundExecutor {

    /**
     * Execute a set of task on the background without waiting for them to finish.
     * If executor is already running stops all previously started tasks.
     *
     * @param tasks  tasks to execute
     */
    void execute(Collection<? extends Runnable> tasks);

    /**
     * Stop execution. Does nothing if execution was not started.
     */
    void stop();

    /**
     * @return true if execution is running, false otherwise
     */
    boolean isRunning();
}
