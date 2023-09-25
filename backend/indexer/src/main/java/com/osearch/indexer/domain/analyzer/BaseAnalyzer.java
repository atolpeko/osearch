package com.osearch.indexer.domain.analyzer;

import static com.osearch.indexer.util.DurationFormatter.formatDuration;

import com.osearch.indexer.domain.entity.AnalyzerContext;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.Callable;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import org.apache.logging.log4j.Level;

/**
 * A base analyzer class.
 * To create a concrete analyzer, extend this class and implement the 'analyze' method.
 */
@Setter
@Log4j2
@RequiredArgsConstructor
public abstract class BaseAnalyzer implements ContentAnalyzer {
    private ContentAnalyzer next;

    /**
     * Calls the `analyze` method of the next Analyzer in the chain, if it exists.
     *
     * @param context the AnalyzerContext object to be passed to the next Analyzer.
     */
    protected void next(AnalyzerContext context) {
        if (next != null) {
            next.analyze(context);
        }
    }

    /**
     * Execute a runnable logging time elapsed.
     *
     * @param logLevel  log level.
     * @param runnable  the runnable to execute.
     * @param message   log message to write.
     * @param params    parameters to the message.
     */
    protected void withDurationLog(
        Level logLevel,
        Runnable runnable,
        String message,
        Object... params
    ) {
        var start = Instant.now();
        runnable.run();
        var timeElapsed = Duration.between(start, Instant.now());
        log.log(logLevel, message + " in " + formatDuration(timeElapsed), params);
    }

    /**
     * Execute a callable logging time elapsed.
     * Uses the result returned by the callable as a first log parameter.
     *
     * @param logLevel  log level.
     * @param callable  the callable to execute.
     * @param message   log message to write.
     * @param params    parameters to the message.
     */
    protected<T> void withDurationLog(
        Level logLevel,
        Callable<T> callable,
        String message,
        Object... params
    ) {
        try {
            var start = Instant.now();
            var reslt = callable.call();
            var timeElapsed = Duration.between(start, Instant.now());
            log.log(logLevel, message + " in " + formatDuration(timeElapsed), reslt,  params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
