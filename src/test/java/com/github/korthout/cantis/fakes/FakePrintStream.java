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
package com.github.korthout.cantis.fakes;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Does not print to an actual target,
 * but keeps track of the printed lines.
 * @since 0.1
 */
public final class FakePrintStream extends PrintStream {

    /**
     * Main constructor.
     */
    public FakePrintStream() {
        super(new FakeOutputStream());
    }

    /**
     * Tells which lines have been printed so far.
     * @return All printed lines
     */
    public List<String> lines() {
        return (
            (FakeOutputStream) this.out
        ).lines();
    }

    /**
     * Does not write to an actual target,
     * but keeps track of the written lines.
     * @since 0.1
     */
    private static final class FakeOutputStream extends OutputStream {

        /**
         * The written lines.
         */
        private final List<String> written;

        /**
         * A line that is currently being written.
         */
        private String line;

        /**
         * Constructor.
         */
        private FakeOutputStream() {
            super();
            this.written = new ArrayList<>(10);
            this.line = "";
        }

        @Override
        public void write(final int bytetowrite) {
            final var text = String.valueOf((char) bytetowrite);
            this.line = this.line.concat(text);
            if (text.equals(System.lineSeparator())) {
                this.written.add(this.line);
                this.line = "";
            }
        }

        /**
         * Tells which lines have been written so far.
         * @return All written lines
         */
        List<String> lines() {
            return this.written;
        }
    }
}
