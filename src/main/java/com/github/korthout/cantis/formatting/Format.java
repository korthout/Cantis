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
import java.util.Locale;
import lombok.NonNull;

/**
 * All supported output formats.
 * @since 0.1.1
 * @checkstyle JavadocVariable (4 lines)
 */
public enum Format {
    PLAIN;

    /**
     * Static factory method for Format objects.
     * @param name The exact name of the format, not case-sensitive but must
     *  match exactly, no extraneous whitespace characters are allowed
     * @return The format with the specified name
     * @see Format#valueOf(String name)
     */
    @SuppressWarnings("PMD.ProhibitPublicStaticMethods")
    public static Format fromString(final @NonNull String name) {
        return Format.valueOf(name.toUpperCase(Locale.ENGLISH));
    }

    /**
     * Builds a formatted glossary.
     * @param glossary The glossary to format
     * @return The formatted glossary
     * @checkstyle MethodName (3 lines)
     */
    @SuppressWarnings("PMD.ShortMethodName")
    public Formatted of(final Glossary glossary) {
        if (this == Format.PLAIN) {
            return new Plain(glossary);
        }
        throw new IllegalArgumentException("Unsupported format");
    }
}
