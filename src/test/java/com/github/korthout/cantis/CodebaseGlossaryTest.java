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
        new CodebaseGlossary((Stream<Classifier>) null);
    }

    @Test
    public void anEmptyListOfClassifiersHasNoDefinitions() {
        Assertions.assertThat(
            new CodebaseGlossary(
                new ListOf<Classifier>().stream()
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
    public void codebaseOfClassifierWithAnnotationAndJavadocHasDefinitions() {
        Assertions.assertThat(
            new CodebaseGlossary(
                new ListOf<Classifier>(
                    new FakeClassifier(
                        "FakeClassifier",
                        "Acts like a Classifier."
                    )
                ).stream()
            ).definitions()
        ).containsExactlyInAnyOrder(
            new Definition("FakeClassifier", "Acts like a Classifier.")
        );
    }

    @Test
    public void codebaseWithClassifierWithoutAnnotationHasNoDefinitions() {
        Assertions.assertThat(
            new CodebaseGlossary(
                new ListOf<Classifier>(
                    new FakeClassifierWithoutAnnotation()
                ).stream()
            ).definitions()
        ).isEmpty();
    }

    @Test
    public void codebaseWithClassifierWithoutJavadocHasNoDefinitions() {
        Assertions.assertThat(
            new CodebaseGlossary(
                new ListOf<Classifier>(
                    new FakeClassifierWithoutJavadoc()
                ).stream()
            ).definitions()
        ).isEmpty();
    }

    @Test
    @SuppressWarnings({
        "PMD.AvoidDuplicateLiterals",
        "PMD.JUnitAssertionsShouldIncludeMessage"
    })
    public void codebaseWithClassifiersHasDefinitionsForTheRightClassifiers() {
        Assertions.assertThat(
            new CodebaseGlossary(
                new ListOf<>(
                    new FakeClassifierWithoutJavadoc(),
                    new FakeClassifier(
                        "FakeClassifier1",
                        "Acts like a Classifier."
                    ),
                    new FakeClassifierWithoutAnnotation(),
                    new FakeClassifier(
                        "FakeClassifier2",
                        "Also acts like a Classifier."
                    ),
                    new FakeClassifierWithoutAnnotation()
                ).stream()
            ).definitions()
        ).containsExactlyInAnyOrder(
            new Definition("FakeClassifier1", "Acts like a Classifier."),
            new Definition("FakeClassifier2", "Also acts like a Classifier.")
        );
    }

    /**
     * Fake object that acts like a Codebase without any classifiers.
     */
    private static final class EmptyCodebase implements Codebase {

        @Override
        public Stream<Classifier> classifiers() {
            return Stream.empty();
        }
    }

    /**
     * Fake object that acts like a Classifier.
     */
    private static final class FakeClassifier implements Classifier {

        /**
         * The name of the classifier.
         */
        private final String name;

        /**
         * The javadoc description of the classifier.
         */
        private final String javadoc;

        FakeClassifier(
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
     * Fake objects that acts like a Classifier
     * that is not annotated with {@code @GlossaryTerm}.
     */
    private static final class FakeClassifierWithoutAnnotation
        implements Classifier {

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
     * Fake object that acts like a Classifier
     * that does not have a JavaDoc description.
     */
    private static final class FakeClassifierWithoutJavadoc
        implements Classifier {

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
