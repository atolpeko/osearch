package com.osearch.indexer.service.executor;

import static com.osearch.indexer.fixture.BackgroundExecutorFixture.TASKS_COUNT;
import static com.osearch.indexer.fixture.BackgroundExecutorFixture.endlessTasks;
import static com.osearch.indexer.fixture.BackgroundExecutorFixture.listModifyingTasks;
import static com.osearch.indexer.fixture.BackgroundExecutorFixture.listModifyingTasksResult;

import static org.awaitility.Awaitility.await;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
        var emptyList = new ArrayList<String>(TASKS_COUNT);
        var tasks = listModifyingTasks(emptyList);

        target.execute(tasks);
        await().atMost(8, TimeUnit.SECONDS)
                .until(() -> !target.isRunning());

        var expectedList = listModifyingTasksResult();
        var resultList = emptyList.stream().sorted().collect(Collectors.toList());
        assertEquals(expectedList, resultList);
    }

    @Test
    void shouldStopExecution() {
        target.execute(endlessTasks());
        await().pollDelay(8, TimeUnit.SECONDS).until(() -> true);
        target.stop();

        assertFalse(target.isRunning());
    }
}