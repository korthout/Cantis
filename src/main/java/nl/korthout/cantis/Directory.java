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
package nl.korthout.cantis;

import java.io.File;
import java.util.Collection;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.cactoos.Scalar;
import org.cactoos.scalar.SolidScalar;

/**
 * A directory of files.
 * @since 0.1
 */
public interface Directory {

    /**
     * Provides the files in this directory.
     * @return The files in the directory
     */
    Collection<File> files();

    /**
     * A directory of source files.
     */
    final class FromSource implements Directory {

        /**
         * Supported file extensions.
         */
        private static final String[] EXTENSIONS = new String[]{"java"};

        /**
         * The source files in the directory.
         */
        private final Scalar<Collection<File>> sources;

        /**
         * Main constructor.
         * @param directory Directory containing the source files
         */
        FromSource(final @NonNull File directory) {
            this.sources = new SolidScalar<>(
                () -> FileUtils.listFiles(
                    directory,
                    FromSource.EXTENSIONS,
                    true
                )
            );
        }

        /**
         * Constructor.
         * @param path Path to the directory containing the source files
         */
        FromSource(final String path) {
            this(new File(path));
        }

        @Override
        @SneakyThrows
        public Collection<File> files() {
            return this.sources.value();
        }
    }
}
