package com.subscribed.clock.validators;

import com.subscribed.clock.exceptions.InvalidIntervalException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IntervalValidator {
    private static final int LOWER_LIMIT_SECONDS = toSeconds(0, 0, 5);
    private static final int UPPER_LIMIT_SECONDS = toSeconds(4, 0, 0);

    private static final IntervalValidator DEFAULT_INTERVAL_VALIDATOR = new IntervalValidator();
    public static IntervalValidator getInstance() {
        return DEFAULT_INTERVAL_VALIDATOR;
    }
    private IntervalValidator() {}
    public boolean isValid(int interval) {
        return isWithinLimits(interval);
    }

    public static String limits() {
        return String.format("[%d, %d] seconds", LOWER_LIMIT_SECONDS, UPPER_LIMIT_SECONDS);
    }

    private static boolean isWithinLimits(int interval) {
        return LOWER_LIMIT_SECONDS <= interval && interval <= UPPER_LIMIT_SECONDS;
    }

    private static int toSeconds(int hours, int minutes, int seconds) {
        return hours * 60 * 60 + minutes * 60 + seconds;
    }
}
