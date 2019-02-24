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

import java.util.stream.Stream;
import lombok.NonNull;
import org.assertj.core.api.Assertions;
import org.cactoos.list.ListOf;
import org.junit.Test;

/**
 * Unit tests for {@code CodebaseGlossary} objects.
 * @since 0.1
 */
@SuppressWarnings("PMD.ProhibitPlainJunitAssertionsRule")
public class CodebaseGlossaryTest {

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNull() {
        new CodebaseGlossary((Stream<Type>) null);
    }

    @Test
    public void anEmptyListOfTypesHasNoDefinitions() {
        Assertions.assertThat(
            new CodebaseGlossary(
                new ListOf<Type>().stream()
            ).definitions()
        ).isEmpty();
    }

    @Test
    public void anEmptyCodebaseHasNoDefinitions() {
        Assertions.assertThat(
            new CodebaseGlossary(
                new EmptyCodebase()
            ).definitions()
        ).isEmpty();
    }

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void codebaseOfTypeWithAnnotationAndJavadocHasDefinitions() {
        Assertions.assertThat(
            new CodebaseGlossary(
                new ListOf<Type>(
                    new FakeType(
                        "FakeType",
                        "Acts like a Type."
                    )
                ).stream()
            ).definitions()
        ).containsExactlyInAnyOrder(
            new Definition("FakeType", "Acts like a Type.")
        );
    }

    @Test
    public void codebaseWithTypeWithoutAnnotationHasNoDefinitions() {
        Assertions.assertThat(
            new CodebaseGlossary(
                new ListOf<Type>(
                    new FakeTypeWithoutAnnotation()
                ).stream()
            ).definitions()
        ).isEmpty();
    }

    @Test
    public void codebaseWithTypeWithoutJavadocHasNoDefinitions() {
        Assertions.assertThat(
            new CodebaseGlossary(
                new ListOf<Type>(
                    new FakeTypeWithoutJavadoc()
                ).stream()
            ).definitions()
        ).isEmpty();
    }

    @Test
    @SuppressWarnings({
        "PMD.AvoidDuplicateLiterals",
        "PMD.JUnitAssertionsShouldIncludeMessage"
    })
    public void codebaseWithTypesHasDefinitionsForTheRightTypes() {
        Assertions.assertThat(
            new CodebaseGlossary(
                new ListOf<>(
                    new FakeTypeWithoutJavadoc(),
                    new FakeType(
                        "FakeType1",
                        "Acts like a Type."
                    ),
                    new FakeTypeWithoutAnnotation(),
                    new FakeType(
                        "FakeType2",
                        "Also acts like a Type."
                    ),
                    new FakeTypeWithoutAnnotation()
                ).stream()
            ).definitions()
        ).containsExactlyInAnyOrder(
            new Definition("FakeType1", "Acts like a Type."),
            new Definition("FakeType2", "Also acts like a Type.")
        );
    }

    /**
     * Fake object that acts like a Codebase without any types.
     */
    private static final class EmptyCodebase implements Codebase {

        @Override
        public Stream<Type> types() {
            return Stream.empty();
        }
    }

    /**
     * Fake object that acts like a Type.
     */
    private static final class FakeType implements Type {

        /**
         * The name of the type.
         */
        private final String name;

        /**
         * The javadoc description of the type.
         */
        private final String javadoc;

        FakeType(
            final @NonNull String name,
            final @NonNull String javadoc
        ) {
            this.name = name;
            this.javadoc = javadoc;
        }

        @Override
        public boolean hasGlossaryTermAnnotation() {
            return true;
        }

        @Override
        public boolean hasJavadoc() {
            return true;
        }

        @Override
        public Definition definition() {
            return new Definition(this.name, this.javadoc);
        }
    }

    /**
     * Fake objects that acts like a Type
     * that is not annotated with {@code @GlossaryTerm}.
     */
    private static final class FakeTypeWithoutAnnotation
        implements Type {

        @Override
        public boolean hasGlossaryTermAnnotation() {
            return false;
        }

        @Override
        public boolean hasJavadoc() {
            return true;
        }

        @Override
        public Definition definition() {
            return null;
        }
    }

    /**
     * Fake object that acts like a Type
     * that does not have a JavaDoc description.
     */
    private static final class FakeTypeWithoutJavadoc
        implements Type {

        @Override
        public boolean hasGlossaryTermAnnotation() {
            return true;
        }

        @Override
        public boolean hasJavadoc() {
            return false;
        }

        @Override
        public Definition definition() {
            return null;
        }
    }
}
