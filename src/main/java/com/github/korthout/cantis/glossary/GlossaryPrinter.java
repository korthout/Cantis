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

import com.github.korthout.cantis.Printer;
import com.github.korthout.cantis.codebase.Directory;
import com.github.korthout.cantis.codebase.FromFiles;
import com.github.korthout.cantis.formatting.Format;
import com.github.korthout.cantis.output.Destination;
import java.time.Duration;
import lombok.NonNull;
import org.cactoos.text.FormattedText;

/**
 * Print a glossary from your sourcecode.
 *
 * <p><b>Note</b>: This class is more procedural than other classes,
 * because it sits at the boundary between the user and the application's
 * internals.</p>
 * @since 0.1.1
 */
public final class GlossaryPrinter implements Printer {

    /**
     * Root directory of the source code.
     */
    private final Directory directory;

    /**
     * Information output goes here.
     */
    private final Destination info;

    /**
     * Formatted glossary output goes here.
     */
    private final Destination target;

    /**
     * The format to output the glossary in.
     */
    private final Format format;

    /**
     * Main Constructor.
     * @param directory Root directory of the source code
     * @param info Information will be outputted to this destination
     * @param target Formatted glossary will be outputted to this destination
     * @param format The glossary will be outputted in this format
     * @checkstyle ParameterNumber (3 lines)
     */
    public GlossaryPrinter(
        final @NonNull Directory directory,
        final @NonNull Destination info,
        final @NonNull Destination target,
        final @NonNull Format format
    ) {
        this.directory = directory;
        this.info = info;
        this.target = target;
        this.format = format;
    }

    /**
     * Shorthand constructor to direct all output to the same destination.
     * @param directory Root directory of the source code
     * @param output The formatted glossary and information will be outputted
     *  to this destination
     * @param format The glossary will be outputted in this format
     */
    public GlossaryPrinter(
        final Directory directory,
        final Destination output,
        final Format format
    ) {
        this(directory, output, output, format);
    }

    @Override
    public void print() {
        final var runnable = new Timed.TimedRunnable(
            () -> {
                this.info.write(
                    new FormattedText(
                        "Scanning %d java files for @Term annotation",
                        this.directory.files().size()
                    ));
                this.target.write(
                    this.format.of(
                        new FromCodebase(
                            new FromFiles(this.directory)
                        )
                    ).formatted()
                );
            });
        final Duration runtime = runnable.runtime();
        this.info.write(
            new FormattedText("Finished in: %ss", runtime.toSeconds())
        );
    }

}
