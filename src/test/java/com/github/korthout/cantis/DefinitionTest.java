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

import org.assertj.core.api.Assertions;
import org.cactoos.text.TextOf;
import org.junit.Test;

/**
 * Unit tests for {@code Definition} objects.
 * @since 0.1
 */
@SuppressWarnings({
    "PMD.ProhibitPlainJunitAssertionsRule",
    "PMD.AvoidDuplicateLiterals"
})
public class DefinitionTest {

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedInConstructorAsTerm() {
        new Definition(null, "");
    }

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedInConstructorAsDefinition() {
        new Definition("", null);
    }

    @Test
    public void textIsATextualRepresentationOfTheDefinition() {
        Assertions.assertThat(
            new Definition(
                "Glossary",
                "A list of definitions in a codebase."
            ).text()
        ).isEqualTo(
            new TextOf(
                "Glossary: A list of definitions in a codebase."
            )
        );
    }

    @Test
    @SuppressWarnings("PMD.LinguisticNaming")
    public void toStringIsATextualRepresentationOfTheDefinition() {
        Assertions.assertThat(
            new Definition(
                "Glossary",
                "A list of definitions in a codebase."
            ).toString()
        ).isEqualTo("Glossary: A list of definitions in a codebase.");
    }

    @SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
    @Test(expected = NullPointerException.class)
    public void compareDoesNotAllowNull() {
        new Definition(
            "Glossary",
            "A list of definitions in a codebase."
        ).compareTo(null);
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    public void definitionsCanBeCompared() {
        final var author = new Definition(
            "Author",
            "The originator or creator of a work."
        );
        final var code = new Definition(
            "Code",
            "Instructions for a computer."
        );
        Assertions.assertThat(author)
            .isLessThan(code)
            .isEqualByComparingTo(author);
        Assertions.assertThat(code)
            .isGreaterThan(author)
            .isEqualByComparingTo(code);
    }
}
