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
package com.github.korthout.cantis.output;

import java.io.File;
import java.nio.file.Files;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.cactoos.Text;

/**
 * Writes text lines to a File.
 * @since 0.1.1
 */
public final class ToFile implements Destination {

    /**
     * Output gets written to here.
     */
    private final File file;

    /**
     * Constructor.
     * @param path Path to the file
     */
    public ToFile(final String path) {
        this(new File(path));
    }

    /**
     * Constructor.
     * @param file File to write to
     */
    public ToFile(final @NonNull File file) {
        this.file = file;
    }

    @Override
    @SneakyThrows
    public void write(final @NonNull Text line) {
        Files.writeString(
            this.file.toPath(),
            line.asString() + System.lineSeparator()
        );
    }
}
