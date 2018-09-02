package nl.korthout.cantis;

import java.time.Duration;
import java.time.Instant;

import lombok.NonNull;

/**
 * Decorator to time the execution of a Runnable.
 */
final class TimedRunnable implements Timed {

    private final Runnable runnable;

    /**
     * Constructor.
     * @param runnable The runnable to time
     */
    TimedRunnable(@NonNull Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public Duration runtime() {
        var start = Instant.now();
        runnable.run();
        return Duration.between(start, Instant.now());
    }
}
