package com.osearch.ranker.domain.valueobject;

import lombok.Data;

/**
 * The Significance class represents the significance or importance of a topic
 * comparing to other topics mentioned on a page.
 *
 * <p>
 * The value must be between 0 and 100, inclusive.
 * </p>
 */
@Data
public class Significance implements Comparable<Significance> {
    private final double value;

    /**
     * Constructs a new Significance object with the given value.
     *
     * @param value the value to set for the Significance object
     * @return a new Significance object with the given value
     * @throws IllegalArgumentException if the value is less than 0 or greater than 100
     */
    public static Significance of(double value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("Percentage value must be between 0 and 100.");
        }

        return new Significance(value);
    }

    /**
     * Represents the significance of a topic as a percentage.
     *
     * @param value the significance value as a percentage
     * @throws IllegalArgumentException if the value is less than 0 or greater than 100.
     */
    public Significance(double value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("Percentage value must be between 0 and 100.");
        }

        this.value = value;
    }

    @Override
    public int compareTo(Significance other) {
        return Double.compare(value, other.getValue());
    }
}
