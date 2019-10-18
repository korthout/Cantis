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
package com.github.korthout.cantis.from_java.codebase;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseStart;
import com.github.javaparser.Providers;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.korthout.cantis.from_java.Codebase;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.NonNull;

/**
 * A collection of code constructed from source files.
 * @since 0.2
 */
public final class FromFiles implements Codebase {

    /**
     * The sources that form this codebase.
     */
    private final Collection<File> sources;

    /**
     * Can parse the Java code in the codebase.
     */
    private final JavaParser parser;

    /**
     * Main Constructor.
     * @param sources The sources that form this codebase
     */
    public FromFiles(final @NonNull Collection<File> sources) {
        this.sources = sources;
        this.parser = new JavaParser();
    }

    /**
     * Constructor.
     * @param directory The root directory containing the source files
     */
    public FromFiles(final Directory directory) {
        this(directory.files());
    }

    @Override
    public Stream<Type> types() {
        return this.sources.stream()
            .map(this::parse)
            .flatMap(Optional::stream)
            .map(file -> file.findAll(TypeDeclaration.class))
            .flatMap(types -> types.stream().map(Type.TypeFromJavaparser::new));
    }

    /**
     * Parses the file.
     * @param file The file containing the code to parse
     * @return An optional object with an object that describes
     *  the parsed code, or an empty optional object
     * @checkstyle ReturnCount (2 lines)
     */
    @SuppressWarnings("PMD.OnlyOneReturn")
    private Optional<CompilationUnit> parse(final File file) {
        try {
            return this.parser.parse(
                ParseStart.COMPILATION_UNIT,
                Providers.provider(file)
            ).getResult();
        } catch (final FileNotFoundException exception) {
            return Optional.empty();
        }
    }
}
