package com.osearch.indexer.service.executor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Component;

@Component
@Log4j2
public class BackgroundExecutorImpl implements BackgroundExecutor {
    private final List<Thread> threads = new ArrayList<>();

    @Override
    public void execute(Collection<? extends Runnable> tasks) {
        if (isRunning()) {
            stop();
        }

        for (var task: tasks) {
            var thread = new Thread(task);
            thread.setDaemon(true);
            threads.add(thread);
        }

        log.debug("Running {} threads: {}", threads.size(), threads);
        threads.forEach(Thread::start);
    }

    @Override
    public boolean isRunning() {
        return threads.stream().anyMatch(Thread::isAlive);
    }

    @Override
    public void stop() {
        log.debug("Stopping {} threads", threads.size());
        if (isRunning()) {
            threads.forEach(Thread::interrupt);
        }

        threads.clear();
    }
}
