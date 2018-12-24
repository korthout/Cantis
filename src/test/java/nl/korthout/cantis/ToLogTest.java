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
package nl.korthout.cantis;

import java.util.LinkedList;
import java.util.List;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.assertj.core.api.Assertions;
import org.cactoos.text.TextOf;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@code ToLog} objects.
 * @since 0.1
 */
@SuppressWarnings({
    "PMD.ProhibitPlainJunitAssertionsRule",
    "PMD.AvoidDuplicateLiterals"
})
public class ToLogTest {

    /**
     * The log to verify what lines were written.
     */
    private FakeLog log;

    @Before
    public void before() {
        this.log = new FakeLog();
    }

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNull() {
        new ToLog(null);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ConstantConditions")
    public void writeDoesNotAllowNull() {
        new ToLog(
            this.log
        ).write(null);
    }

    @Test
    public void everyWrittenLineEndsWithALineEnding() {
        new ToLog(
            this.log
        ).write(new TextOf(""));
        Assertions.assertThat(
            this.log.lines()
        ).contains("");
    }

    @Test
    public void linesAreWrittenInOrder() {
        final var destination = new ToLog(this.log);
        destination.write(new TextOf("Some Text"));
        destination.write(new TextOf("Another Text"));
        destination.write(new TextOf("For Good Measure"));
        Assertions.assertThat(
            this.log.lines()
        ).containsExactly(
            "Some Text",
            "Another Text",
            "For Good Measure"
        );
    }

    /**
     * Fake object that acts like a {@code Log}
     * that does not log to an actual target,
     * but keeps track of the logged lines.
     * @since 0.1
     */
    private final class FakeLog extends SystemStreamLog {

        /**
         * The lines that were logged.
         */
        private final List<String> logged;

        private FakeLog() {
            super();
            this.logged = new LinkedList<>();
        }

        @Override
        public void info(final CharSequence line) {
            this.logged.add(line.toString());
        }

        List<String> lines() {
            return this.logged;
        }
    }
}
