package nl.korthout.cantis;

import java.time.Duration;

/**
 * Can have its runtime measured.
 */
public interface Timeable {

    /**
     * Tells how long it took to run.
     * @return Duration of runtime
     */
    Duration runtime();

}
