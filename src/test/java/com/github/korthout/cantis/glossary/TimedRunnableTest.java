/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Nico Korthout
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.korthout.cantis.glossary;

import java.time.Duration;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Unit tests for {@code TimedRunnable} objects.
 * @since 0.1.1
 */
@SuppressWarnings("PMD.ProhibitPlainJunitAssertionsRule")
public class TimedRunnableTest {

    @Test(expected = NullPointerException.class)
    public void nullIsNotNotAllowedInConstructor() {
        new Timed.TimedRunnable(null);
    }

    @Test
    public void constructorDoesNotRunTheRunnable() {
        final var runnable = new AssertableRunnable();
        new Timed.TimedRunnable(runnable);
        Assertions.assertThat(runnable.ran()).isFalse();
    }

    @Test
    public void runtimeActuallyRunsTheRunnable() {
        final var runnable = new AssertableRunnable();
        new Timed.TimedRunnable(runnable).runtime();
        Assertions.assertThat(runnable.ran()).isTrue();
    }

    @Test
    public void runtimeIsSomewhatQuick() {
        final int maximum = 50;
        Assertions.assertThat(new Timed.TimedRunnable(() -> { }).runtime())
            .isGreaterThan(Duration.ZERO)
            .isLessThan(Duration.ofMillis(maximum));
    }

    @Test
    public void runtimeIsDependentOnExecutionTimeOfRunnable() {
        final int minimum = 50;
        final int maximum = 100;
        Assertions.assertThat(
            new Timed.TimedRunnable(
                new SlowRunnable(minimum)
            ).runtime()
        ).isGreaterThan(
            Duration.ofMillis(minimum)
        ).isLessThan(
            Duration.ofMillis(maximum)
        );
    }

    /**
     * Knows whether its run method has been executed.
     * @since 0.1
     */
    private static class AssertableRunnable implements Runnable {

        /**
         * Whether or not the runnable was executed.
         */
        private boolean executed;

        @Override
        public void run() {
            this.executed = true;
        }

        private boolean ran() {
            return this.executed;
        }
    }

    /**
     * Executes its run method with a delay to make it slow.
     * @since 0.1
     */
    private static final class SlowRunnable implements Runnable {

        /**
         * The time this runnable will try to run.
         */
        private final int runtime;

        private SlowRunnable(final int runtime) {
            this.runtime = runtime;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(this.runtime);
            } catch (final InterruptedException exception) {
                Assertions.fail("Thread was interrupted", exception);
            }
        }
    }
}
