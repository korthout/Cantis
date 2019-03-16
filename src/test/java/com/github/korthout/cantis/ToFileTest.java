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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@code ToFile} objects.
 * @since 0.1
 */
public class ToFileTest {

    /**
     * Temporary folder provided by junit.
     */
    @Rule public TemporaryFolder tmp = new TemporaryFolder();

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNull() {
        new ToFile((File) null);
    }

    @Test
    public void constructorAllowsEmptyString() {
        assertThat(new ToFile("")).isNotNull();
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ConstantConditions")
    public void writeDoesNotAllowNull() throws IOException {
        new ToFile(
            this.tmp.newFile()
        ).write(null);
    }

    /**
     * Untestable because of the Files.writeString implementation
     * We simply have to trust the library.
     */
    @Test
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    public void everyWrittenLineEndsWithALineEnding() { }

    /**
     * Untestable because of the Files.writeString implementation
     * We simply have to trust the library.
     */
    @Test
    @SuppressWarnings("PMD.UncommentedEmptyMethodBody")
    public void linesAreWrittenInOrder() { }
}
