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
package com.github.korthout.cantis.from_java.glossary;

import java.time.Duration;
import java.time.Instant;
import lombok.NonNull;

/**
 * Measures its runtime.
 * @since 0.2
 */
public interface Timed {

    /**
     * Tells how long it took to run.
     * @return Duration of runtime
     */
    Duration runtime();

    /**
     * Times the execution runtime of a Runnable.
     * @since 0.2
     */
    final class TimedRunnable implements Timed {

        /**
         * The runnable to time.
         */
        private final Runnable runnable;

        /**
         * Constructor.
         * @param runnable The runnable to time
         */
        public TimedRunnable(final @NonNull Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public Duration runtime() {
            final var start = Instant.now();
            this.runnable.run();
            return Duration.between(start, Instant.now());
        }
    }
}