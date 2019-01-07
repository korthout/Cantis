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
package com.github.korthout.cantis;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.cactoos.Text;
import org.cactoos.list.ListOf;
import org.junit.Test;

/**
 * Unit tests for {@code PrintableGlossary} objects.
 * @since 0.1
 */
@SuppressWarnings("PMD.ProhibitPlainJunitAssertionsRule")
public class PrintableGlossaryTest {

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedAsDirectory() {
        new PrintableGlossary(null, new FakeDestination());
    }

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedAsOutput() {
        new PrintableGlossary(() -> new ListOf<File>(), null);
    }

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedAsInfo() {
        new PrintableGlossary(() -> new ListOf<File>(), null, new FakeDestination());
    }

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedAsTarget() {
        new PrintableGlossary(() -> new ListOf<File>(), new FakeDestination(), null);
    }

    @Test
    public void printsNumberOfFiles() {
        final var info = new FakeDestination();
        new PrintableGlossary(
            () -> new ListOf<File>(),
            info,
            new FakeDestination()
        ).print();
        Assertions.assertThat(
            info.lines()
        ).contains(
            // @checkstyle StringLiteralsConcatenation (2 lines)
            "Scanning 0 java files for @GlossaryTerm annotation"
                + System.lineSeparator()
        );
    }

    @Test
    public void printsGlossaryToDestination() {
        final var destination = new FakeDestination();
        new PrintableGlossary(
            () -> new ListOf<File>(),
            new FakeDestination(),
            destination
        ).print();
        Assertions.assertThat(
            destination.lines()
        ).contains(
            // @checkstyle StringLiteralsConcatenation (1 lines)
            "No definitions found." + System.lineSeparator()
        );
    }

    @Test
    public void printsFinished() {
        final var info = new FakeDestination();
        new PrintableGlossary(
            () -> new ListOf<File>(),
            info,
            new FakeDestination()
        ).print();
        Assertions.assertThat(
            info.lines()
        ).contains(
            // @checkstyle StringLiteralsConcatenation (1 lines)
            "Finished in: 0s" + System.lineSeparator()
        );
    }

    /**
     * Fake object that acts like a {@code Destination}
     * that allows to check what lines were written.
     * @since 0.1
     */
    public final class FakeDestination implements Destination {

        /**
         * The lines written to this destination.
         */
        private final List<String> written = new LinkedList<>();

        @Override
        @SneakyThrows
        public void write(final @NonNull Text output) {
            // @checkstyle StringLiteralsConcatenation (1 lines)
            this.written.add(output.asString() + System.lineSeparator());
        }

        List<String> lines() {
            return this.written;
        }
    }
}
