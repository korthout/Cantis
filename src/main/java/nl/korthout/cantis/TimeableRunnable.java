package nl.korthout.cantis;

import java.time.Duration;
import java.time.Instant;

import lombok.NonNull;

/**
 * Times the execution runtime of a Runnable.
 */
public final class TimeableRunnable implements Timeable {

    private final Runnable runnable;

    /**
     * Constructor.
     * @param runnable The runnable to time
     */
    TimeableRunnable(@NonNull Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public Duration runtime() {
        var start = Instant.now();
        runnable.run();
        return Duration.between(start, Instant.now());
    }
}
