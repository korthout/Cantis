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
package com.github.korthout.cantis.formatting;

import com.github.korthout.cantis.Formatted;
import com.github.korthout.cantis.Glossary;
import lombok.NonNull;
import org.cactoos.Text;
import org.cactoos.text.FormattedText;
import org.cactoos.text.Joined;
import org.cactoos.text.TextOf;

/**
 * JSON formatted glossary.
 * @since 0.2
 */
public final class Json implements Formatted {

    /**
     * The glossary to format.
     */
    private final Glossary glossary;

    /**
     * Main constructor.
     * @param glossary The glossary to format
     */
    public Json(final @NonNull Glossary glossary) {
        this.glossary = glossary;
    }

    @Override
    public Text formatted() {
        return new FormattedText(
            new TextOf("{%n    \"definitions\": [%s]%n}"),
            this.definitions()
        );
    }

    /**
     * Formats the definitions value of the glossary.
     * @return Text object of JSON formatted definitions
     */
    private Text definitions() {
        return this.glossary.definitions()
            .map(definition -> definition.json("    "))
            .reduce(Json::join)
            .orElse(new TextOf(""));
    }

    /**
     * Joins a formatted {@code Definition} object to others.
     * @param formatted The already formatted definition objects
     * @param definition The definition object to join
     * @return A new text object with both texts joined
     */
    private static Text join(final Text formatted, final Text definition) {
        return new Joined(
            new TextOf(", "),
            formatted, definition
        );
    }
}
