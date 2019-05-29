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
package com.github.korthout.cantis.from_java.formatting;

import com.github.korthout.cantis.from_java.Glossary;
import com.github.korthout.cantis.from_java.glossary.Definition;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.cactoos.text.TextOf;
import org.junit.Test;

/**
 * Unit tests for {@code Plain} objects.
 * @since 0.2
 */
@SuppressWarnings("PMD.ProhibitPlainJunitAssertionsRule")
public class PlainTest {

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNull() {
        new Plain(null);
    }

    @Test
    public void emptyGlossaryCanBeFormatted() {
        Assertions.assertThat(
            new Plain(
                new EmptyGlossary()
            ).formatted()
        ).isEqualTo(
            new TextOf("No definitions found.")
        );
    }

    @Test
    public void glossaryCanBeFormatted() {
        Assertions.assertThat(
            new Plain(
                new SimpleCodingGlossary()
            ).formatted()
        ).isEqualTo(
            new TextOf(
                // @checkstyle StringLiteralsConcatenation (2 lines)
                "Author: The originator or creator of a work.\n"
                    + "Code: Instructions for a computer."
            )
        );
    }

    /**
     * Fake object that acts like a {@code Glossary}
     * without any {@code Definitions}.
     * @since 0.2
     */
    private static final class EmptyGlossary implements Glossary {

        @Override
        public Stream<Definition> definitions() {
            return Stream.empty();
        }
    }

    /**
     * Fake object that acts like a {@code Glossary}
     * with 2 {@code Definitions}.
     * @since 0.2
     */
    private static final class SimpleCodingGlossary implements Glossary {

        @Override
        public Stream<Definition> definitions() {
            return Stream.of(
                new Definition(
                    "Author",
                    "The originator or creator of a work."
                ),
                new Definition(
                    "Code",
                    "Instructions for a computer."
                )
            );
        }
    }
}
