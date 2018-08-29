package nl.korthout.cantis;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TimedRunnableTest {

    @Test(expected = NullPointerException.class)
    public void nullIsNotNotAllowedInConstructor() {
        new TimedRunnable(null);
    }

    @Test
    public void runtimeActuallyRuns() {
        var runnable = mock(Runnable.class);
        new TimedRunnable(runnable).runtime();
        verify(runnable).run();
    }

    @Test
    public void runtimeIsSomewhatQuick() {
        assertThat(new TimedRunnable(() -> {}).runtime())
                .isGreaterThan(Duration.ZERO)
                .isLessThan(Duration.ofMillis(50));
    }

    @Test
    public void runtimeIsDependentOnExecutionTimeOfRunnable() {
        assertThat(
                new TimedRunnable(() -> {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        Assertions.fail("Thread was interrupted", e);
                    }
                }).runtime())
                .isGreaterThan(Duration.ofMillis(100))
                .isLessThan(Duration.ofMillis(150));
    }

}