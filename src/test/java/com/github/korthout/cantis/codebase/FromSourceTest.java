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
package com.github.korthout.cantis.codebase;

import com.github.korthout.cantis.codebase.Directory.FromSource;
import java.io.File;
import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Unit tests for {@code FromSource} objects.
 * @since 0.2
 */
public class FromSourceTest {

    /**
     * Temporary folder provided by junit.
     */
    @Rule
    public TemporaryFolder tmp = new TemporaryFolder();

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ConstantConditions")
    public void constructorDoesNotAllowNull() {
        new FromSource((File) null);
    }

    @Test
    public void nonexistingDirectoryResultsInException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(
                () -> new FromSource(new File("nonexisting")).files()
            );
    }

    @Test
    public void nonDirectoryResultsInException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(
                () -> new FromSource(this.tmp.newFile()).files()
            );
    }

    @Test
    public void emptyDirectoryResultsInEmptyCollectionOfFiles() {
        Assertions.assertThat(
            new FromSource(
                this.tmp.getRoot()
            ).files()
        ).isEmpty();
    }

    @Test
    public void pathToEmptyDirectoryResultsInEmptyCollection() {
        Assertions.assertThat(
            new FromSource(
                this.tmp.getRoot().getPath()
            ).files()
        ).isEmpty();
    }

    @Test
    public void directoryWithoutJavaFilesResultsInEmptyCollection()
        throws IOException {
        this.tmp.newFile("Example.txt");
        Assertions.assertThat(
            new FromSource(
                this.tmp.getRoot()
            ).files()
        ).isEmpty();
    }

    @Test
    public void directoryWithJavaFilesResultsInCollectionOfThatOneJavaFile()
        throws IOException {
        final var file = this.tmp.newFile("Example.java");
        Assertions.assertThat(
            new FromSource(
                this.tmp.getRoot()
            ).files()
        ).containsExactly(file);
    }

    @Test
    @SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
    public void directoryWithNestedJavaFileResultsInCollectionOfThatJavaFile()
        throws IOException {
        final File file = this.tmp.newFile("FirstExample.java");
        final var nestedfolder = new TemporaryFolder(this.tmp.getRoot());
        nestedfolder.create();
        final File nested = nestedfolder.newFile("AnotherExample.java");
        Assertions.assertThat(
            new FromSource(
                this.tmp.getRoot()
            ).files()
        ).containsExactlyInAnyOrder(file, nested);
    }

}
