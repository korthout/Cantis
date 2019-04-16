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

import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.maven.plugin.logging.Log;
import org.cactoos.Text;

/**
 * Writes text lines to a Maven Log.
 * @since 0.2
 */
public final class ToLog implements Destination {

    /**
     * Output gets written to here.
     */
    private final Log log;

    /**
     * Constructor.
     * @param log The log to write to
     */
    public ToLog(final @NonNull Log log) {
        this.log = log;
    }

    @Override
    @SneakyThrows
    public void write(final Text line) {
        this.log.info(line.asString());
    }
}
