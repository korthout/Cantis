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
import java.io.IOException;
import java.util.function.Predicate;
import lombok.NonNull;
import com.github.korthout.cantis.Codebase.CodebaseFromFiles;
import com.github.korthout.cantis.fakes.FakeFile;
import org.assertj.core.api.Assertions;
import org.cactoos.list.ListOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Unit tests for {@code CodebaseFromFiles} objects.
 * @since 0.1
 */
@SuppressWarnings({
    "PMD.ProhibitPlainJunitAssertionsRule",
    "PMD.AvoidDuplicateLiterals"
})
public class CodebaseFromFilesTest {

    /**
     * Temporary folder provided by junit.
     */
    @Rule public TemporaryFolder tmp = new TemporaryFolder();

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ConstantConditions")
    public void nullIsNotAllowedAsDirectoryInConstructor() {
        new CodebaseFromFiles((Directory) null);
    }

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedInMainConstructor() {
        new CodebaseFromFiles(() -> null);
    }

    @Test
    public void codebaseOfNoSourcesHasNoClassifiers() {
        Assertions.assertThat(
            new CodebaseFromFiles(
                new ListOf<>()
            ).classifiers()
        ).isEmpty();
    }

    @Test
    public void codebaseCanBeConstructedFromDirectory() {
        Assertions.assertThat(
            new CodebaseFromFiles(
                ListOf<File>::new
            ).classifiers()
        ).isEmpty();
    }

    @Test
    public void codebaseOfTextFileHasNoClassifiers() throws IOException {
        Assertions.assertThat(
            new CodebaseFromFiles(
                new ListOf<>(
                    new FakeFile(this.tmp.newFile("TextFile.txt"))
                        .withContent("Just a simple text file.")
                )
            ).classifiers()
        ).isEmpty();
    }

    @Test
    public void codebaseOfJavaFileHasAClassifier() throws IOException {
        Assertions.assertThat(
            new CodebaseFromFiles(
                new ListOf<>(
                    new FakeFile(this.tmp.newFile("Simple.java"))
                        .withContent("class Simple { }")
                )
            ).classifiers()
        )
            .hasSize(1)
            .allMatch(
                new HasDefinition(
                    new Definition(
                        "Simple",
                        "Description not found"
                    )
                )
            );
    }

    @Test
    public void codebaseOfAnnotatedJavaFileHasClassifierWithAnnotation()
        throws IOException {
        Assertions.assertThat(
            new CodebaseFromFiles(
                new ListOf<>(
                    new FakeFile(this.tmp.newFile("Annotated.java"))
                        .withContent("@GlossaryTerm class Annotated { }")
                )
            ).classifiers()
        )
            .hasSize(1)
            .allMatch(
                new HasDefinition(
                    new Definition(
                        "Annotated",
                        "Description not found"
                    )
                )
            );
    }

    // todo: change back to single line after Qulice fixes regex check
    // see: https://github.com/teamed/qulice/issues/975
    // and: https://github.com/teamed/qulice/issues/976
    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void codebaseOfJavaFileWithJavadocHasClassifierWithJavadoc()
        throws IOException {
        Assertions.assertThat(
            new CodebaseFromFiles(
                new ListOf<>(
                    new FakeFile(this.tmp.newFile("Javadoc.java"))
                        // @checkstyle StringLiteralsConcatenation (4 lines)
                        .withContent("/**"
                            + " * Documentation"
                            + " */"
                            + "class Javadoc { }"
                        )
                )
            ).classifiers()
        )
            .hasSize(1)
            .allMatch(
                new HasDefinition(
                    new Definition(
                        "Javadoc",
                        "Documentation"
                    )
                )
            );
    }

    // todo: change back to single line after Qulice fixes regex check
    // see: https://github.com/teamed/qulice/issues/975
    // and: https://github.com/teamed/qulice/issues/976
    @Test
    public void codebaseCanContainManyClassifiers() throws IOException {
        Assertions.assertThat(
            new CodebaseFromFiles(
                new ListOf<>(
                    new FakeFile(this.tmp.newFile("Simple.java"))
                        .withContent("class Simple { }"),
                    new FakeFile(this.tmp.newFile("Annotated.java"))
                        .withContent("@GlossaryTerm class Annotated { }"),
                    new FakeFile(this.tmp.newFile("Javadoc.java"))
                        // @checkstyle StringLiteralsConcatenation (4 lines)
                        .withContent("/**"
                            + " * Documentation "
                            + " */"
                            + "class Javadoc { }"
                        )
                )
            ).classifiers()
        // @checkstyle MagicNumber (1 lines)
        ).hasSize(3);
    }

    /**
     * Predicate to check whether a {@code Classifier} has
     * a specific {@code Definition}.
     */
    private class HasDefinition implements Predicate<Classifier> {

        /**
         * The definition to match the classifier against.
         */
        private final Definition definition;

        /**
         * Constructor.
         * @param definition The definition to match against
         */
        HasDefinition(final @NonNull Definition definition) {
            this.definition = definition;
        }

        @Override
        public boolean test(final Classifier classifier) {
            return this.definition.equals(classifier.definition());
        }
    }
}
