package nl.korthout.cantis.command;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

public class TimedRunnableTest {

    @Test(expected = NullPointerException.class)
    public void nullIsNotNotAllowedInConstructor() {
        new TimedRunnable(null);
    }

    @Test
    public void constructorDoesNotRunTheRunnable() {
        var runnable = new AssertableRunnable();
        new TimedRunnable(runnable);
        assertThat(runnable.ran()).isFalse();
    }

    @Test
    public void runtimeActuallyRunsTheRunnable() {
        var runnable = new AssertableRunnable();
        new TimedRunnable(runnable).runtime();
        assertThat(runnable.ran()).isTrue();
    }

    @Test
    public void runtimeIsSomewhatQuick() {
        assertThat(new TimedRunnable(() -> {}).runtime())
                .isGreaterThan(Duration.ZERO)
                .isLessThan(Duration.ofMillis(50));
    }

    @Test
    public void runtimeIsDependentOnExecutionTimeOfRunnable() {
        assertThat(new TimedRunnable(new SlowRunnable()).runtime())
                .isGreaterThan(Duration.ofMillis(100))
                .isLessThan(Duration.ofMillis(150));
    }

    /**
     * Knows whether its run method has been executed.
     */
    private static class AssertableRunnable implements Runnable {

        private boolean ran;

        @Override
        public void run() {
            ran = true;
        }

        boolean ran() {
            return ran;
        }
    }

    /**
     * Executes its run method with a delay to make it slow.
     */
    private static class SlowRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Assertions.fail("Thread was interrupted", e);
            }
        }
    }
}