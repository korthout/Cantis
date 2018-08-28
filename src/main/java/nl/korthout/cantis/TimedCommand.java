package nl.korthout.cantis;

import java.time.Duration;
import java.time.Instant;

public abstract class TimedCommand implements Runnable {

    @Override
    public void run() {
        var start = Instant.now();
        execute();
        System.out.println("Finished in: " + Duration.between(start, Instant.now()).toSeconds() + "s");
    }

    abstract void execute();
}
