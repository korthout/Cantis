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

import com.github.korthout.cantis.fakes.FakePrintStream;
import com.github.korthout.cantis.formatting.Format;
import com.github.korthout.cantis.glossary.Definition;
import com.github.korthout.cantis.glossary.GlossaryPrinter;
import com.github.korthout.cantis.output.ToPrintStream;
import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import lombok.NonNull;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

/**
 * Integration test to verify the correct generation
 * of a glossary from some Java files.
 * @since 0.1
 */
@SuppressWarnings({
    "PMD.ProhibitPlainJunitAssertionsRule",
    "PMD.AvoidDuplicateLiterals"
})
public class GenerateGlossaryIT {

    /**
     * The source files to generate the glossary from.
     */
    private List<File> sources;

    /**
     * The output is written to this.
     */
    private FakePrintStream out;

    @Before
    public void initExampleSourceFiles() {
        this.sources = new LinkedList<>();
        this.out = new FakePrintStream();
    }

    @Test
    public void testOneClassFile() {
        this.addSourceFile("com/github/korthout/cantis/example/Example.java");
        new GlossaryPrinter(
            () -> this.sources,
            new ToPrintStream(this.out),
            Format.PLAIN
        ).print();
        Assertions.assertThat(
            this.out.lines()
        ).contains(
            new Definition(
                "Example",
                "This is just a simple example class."
            // @checkstyle StringLiteralsConcatenation (1 lines)
            ).toString() + System.lineSeparator()
        );
    }

    @Test
    @SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
    public void testTwoClassFiles() {
        this.addSourceFile("com/github/korthout/cantis/example/Example.java");
        this.addSourceFile("com/github/korthout/cantis/example/Example2.java");
        new GlossaryPrinter(
            () -> this.sources,
            new ToPrintStream(this.out),
            Format.PLAIN
        ).print();
        Assertions.assertThat(
            this.out.lines()
        ).contains(
            new Definition(
                "Example",
                "This is just a simple example class."
            // @checkstyle StringLiteralsConcatenation (1 lines)
            ).toString() + System.lineSeparator(),
            new Definition(
                "Example2",
                "This is just another simple example class."
            // @checkstyle StringLiteralsConcatenation (1 lines)
            ).toString() + System.lineSeparator()
        );
    }

    private void addSourceFile(final @NonNull String filename) {
        final URL resource = Thread.currentThread()
            .getContextClassLoader()
            .getResource(filename);
        Assertions.assertThat(resource).isNotNull();
        this.sources.add(new File(resource.getFile()));
    }
}
