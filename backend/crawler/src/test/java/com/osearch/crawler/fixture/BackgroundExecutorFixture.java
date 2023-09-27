package com.osearch.crawler.fixture;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BackgroundExecutorFixture {
    public static final int TASKS_COUNT = 2;

    public static List<Runnable> infiniteTasks() {
        IntFunction<Runnable> taskSupplier = id ->
            () ->  { while (true) {} };

        return IntStream.range(1, TASKS_COUNT + 1)
            .mapToObj(taskSupplier)
            .collect(Collectors.toList());
    }
}
