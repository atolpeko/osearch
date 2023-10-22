package com.osearch.indexer.util;

import java.time.Duration;

/**
 * The DurationFormatter class provides a utility method
 * to format a Duration object into a human-readable string representation.
 */
public class DurationFormatter {

    /**
     * Formats a Duration object into a human-readable string representation.
     * If the duration is greater than or equal to 1 minute,
     * the output will be in minutes with the format "{number} minutes".
     * If the duration is less than 1 minute but greater than or equal to 1 second,
     * the output will be in seconds with the format "{number} seconds".
     * If the duration is less than 1 second,
     * the output will be in milliseconds with the format "{number} ms".
     *
     * @param duration the Duration object to be formatted.
     * @return a human-readable string representation of the duration.
     */
    public static String formatDuration(Duration duration) {
        if (duration.toMillis() >= 60000) {
            return String.format("%d minutes", duration.toMinutes());
        }

        return duration.toMillis() >= 1000
            ? String.format("%d seconds", duration.toSeconds())
            : String.format("%d ms", duration.toMillis());
    }

    private DurationFormatter() {}
}
