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
package com.github.korthout.cantis.output;

import com.github.korthout.cantis.fakes.FakePrintStream;
import org.assertj.core.api.Assertions;
import org.cactoos.text.TextOf;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@code ToPrintStream} objects.
 * @since 0.1.1
 */
@SuppressWarnings("PMD.ProhibitPlainJunitAssertionsRule")
public class ToPrintStreamTest {

    /**
     * The fake {@code PrintStream} to write output to.
     */
    private FakePrintStream out;

    @Before
    public void before() {
        this.out = new FakePrintStream();
    }

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNull() {
        new ToPrintStream(null);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ConstantConditions")
    public void writeDoesNotAllowNull() {
        new ToPrintStream(
            new FakePrintStream()
        ).write(null);
    }

    @Test
    public void everyWrittenLineEndsWithALineEnding() {
        new ToPrintStream(
            this.out
        ).write(new TextOf(""));
        Assertions.assertThat(
            this.out.lines()
        ).contains(System.lineSeparator());
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void linesAreWrittenInOrder() {
        final var destination = new ToPrintStream(this.out);
        destination.write(new TextOf("Some Text"));
        destination.write(new TextOf("Another Text"));
        destination.write(new TextOf("For Good Measure"));
        Assertions.assertThat(
            this.out.lines()
        ).containsExactly(
            String.format("Some Text%s", System.lineSeparator()),
            String.format("Another Text%s", System.lineSeparator()),
            String.format("For Good Measure%s", System.lineSeparator())
        );
    }
}
