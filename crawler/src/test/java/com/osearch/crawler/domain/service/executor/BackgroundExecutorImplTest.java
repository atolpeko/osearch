package com.osearch.crawler.domain.service.executor;

import static com.osearch.crawler.fixture.BackgroundExecutorFixture.infiniteTasks;

import static org.awaitility.Awaitility.await;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("category.UnitTest")
class BackgroundExecutorImplTest {
    private BackgroundExecutorImpl target;

    @BeforeEach
    void setUp() {
        target = new BackgroundExecutorImpl();
    }

    @Test
    void shouldStartExecution() {
        target.execute(infiniteTasks());
        await().atMost(2, TimeUnit.SECONDS)
            .until(() -> target.isRunning());

        assertTrue(target.isRunning());
    }

    @Test
    void shouldStopExecution() {
        target.execute(infiniteTasks());
        await().pollDelay(2, TimeUnit.SECONDS).until(() -> true);
        target.stop();

        assertFalse(target.isRunning());
    }
}