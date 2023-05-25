package com.osearch.crawler.fixture;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BackgroundExecutorFixture {
    public static final int TASKS_COUNT = 3;

    public static List<Runnable> listModifyingTasks(List<String> emptyList) {
        IntFunction<Runnable> taskSupplier = id ->
                () ->  {
            log.info("Task {} is running", id);
            emptyList.add("TASK " + id + " FINISHED");
        };

        return generateTasks(taskSupplier, TASKS_COUNT);
    }

    private static List<Runnable> generateTasks(IntFunction<Runnable> taskSupplier, int count) {
        return IntStream.range(1, count + 1)
                .mapToObj(taskSupplier)
                .collect(Collectors.toList());
    }

    public static List<String> listModifyingTasksResult() {
        return IntStream.range(1, TASKS_COUNT + 1)
                .mapToObj(i -> "TASK " + i + " FINISHED")
                .collect(Collectors.toList());
    }

    public static List<Runnable> endlessTasks() {
        IntFunction<Runnable> taskSupplier = id ->
                () -> {
            try {
                while (true) {
                    log.info("Task {} is running", id);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                log.info("Task {} interrupted", id);
                Thread.currentThread().interrupt();
            }
        };

        return generateTasks(taskSupplier, TASKS_COUNT);
    }
}
