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

import com.github.korthout.cantis.Glossary;
import com.github.korthout.cantis.glossary.Definition;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Unit tests for {@code Format} objects.
 * @since 0.1.1
 */
public class FormatTest {

    @Test
    public void fromStringDoesNotAllowEmptyString() {
        Assertions.assertThatThrownBy(() -> Format.fromString(""))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void fromStringDoesNotAllowUnknownValue() {
        Assertions.assertThatThrownBy(() -> Format.fromString("unknown"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void fromStringCreatesFormatFromAString() {
        Assertions.assertThat(Format.fromString("PLAIN"))
            .isEqualTo(Format.PLAIN);
    }

    @Test
    public void fromStringIgnoresCase() {
        Assertions.assertThat(Format.fromString("pLAIn"))
            .isEqualTo(Format.PLAIN);
    }

    @Test
    public void formatPlainOfGlossaryIsFormattedPlain() {
        Assertions.assertThat(Format.PLAIN.of(new FakeGlossary()))
            .isInstanceOf(Plain.class);
    }

    /**
     * Fake object that acts like an empty {@code Glossary}.
     * @since 0.1.1
     */
    private static final class FakeGlossary implements Glossary {
        @Override
        public Stream<Definition> definitions() {
            return Stream.empty();
        }
    }
}
