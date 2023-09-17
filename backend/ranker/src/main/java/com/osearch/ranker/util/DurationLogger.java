package com.osearch.ranker.util;

import java.time.Duration;
import java.time.Instant;

import org.apache.logging.log4j.Level;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DurationLogger {

    private DurationLogger() {}

    /**
     * Execute runnable logging time elapsed.
     *
     * @param runnable  runnable to execute
     * @param message   log message to write
     * @param logLevel  log level
     */
    public static void withDurationLog(Runnable runnable, String message, Level logLevel) {
        var start = Instant.now();
        runnable.run();
        var timeElapsed = Duration.between(start, Instant.now());
        log.log(logLevel, message + " in " + formatDuration(timeElapsed));
    }

    private static String formatDuration(Duration duration) {
        return duration.toMillis() >= 1000
            ? String.format("%d seconds", duration.toSeconds())
            : String.format("%d ms", duration.toMillis());
    }
}
