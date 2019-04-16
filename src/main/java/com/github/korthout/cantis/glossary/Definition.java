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

import com.github.korthout.cantis.Term;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.cactoos.Text;
import org.cactoos.text.TextOf;

/**
 * A term and its description.
 * @since 0.2
 */
@Term
@EqualsAndHashCode
public final class Definition implements Comparable<Definition> {

    /**
     * The term that is defined.
     */
    private final String term;

    /**
     * The description of the term.
     */
    private final String description;

    /**
     * Constructor.
     * @param term The term that is defined
     * @param description The term's description
     */
    public Definition(
        final @NonNull String term,
        final @NonNull String description
    ) {
        this.term = term;
        this.description = description;
    }

    @Override
    public int compareTo(final @NonNull Definition other) {
        return this.term.compareTo(other.term);
    }

    /**
     * Builds a textual representation of this definition.
     * @return The definition as {@code Text}
     */
    public Text text() {
        return new TextOf(String.format("%s: %s", this.term, this.description));
    }

    /**
     * Builds a json representation of this definition.
     * @param indent String to use as indentation on each line
     * @return The definition as {@code Text}
     */
    public Text json(final String indent) {
        return new TextOf(
            String.format(
                "{%n%s    \"term\": \"%s\",%n%s    \"description\": \"%s\"%n%s}",
                indent,
                this.term,
                indent,
                this.description,
                indent
            )
        );
    }

    @Override
    public String toString() {
        return this.text().toString();
    }
}
