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
package com.github.javaparser;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.nodeTypes.NodeWithJavadoc;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Exploratory tests for the Java Parser library.
 * To try-out and document the usage of this library.
 * @since 0.1
 */
@SuppressWarnings("PMD.ProhibitPlainJunitAssertionsRule")
public class JavaParserExploratoryTest {

    @Test
    public void parseSimpleClass() {
        final var code = "class A { }";
        final Optional<ClassOrInterfaceDeclaration> aclass = new JavaParser()
            .parse(code)
            .getResult()
            .orElseThrow()
            .getClassByName("A");
        Assertions.assertThat(aclass)
            .map(NodeWithSimpleName::getNameAsString)
            .contains("A");
    }

    // todo: change back to single line after Qulice fixes regex check
    // see: https://github.com/teamed/qulice/issues/975
    // and: https://github.com/teamed/qulice/issues/976
    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void parseClassWithJavadocComment() {
        // @checkstyle StringLiteralsConcatenation (4 lines)
        final var code = "/**"
            + " * B is a simple class"
            + " */ "
            + "class B { }";
        final Optional<JavadocComment> comment = new JavaParser()
            .parse(code)
            .getResult()
            .orElseThrow()
            .getClassByName("B")
            .flatMap(NodeWithJavadoc::getJavadocComment);
        Assertions.assertThat(comment)
            .map(JavadocComment::parse)
            .map(Javadoc::getDescription)
            .map(JavadocDescription::toText)
            .contains("B is a simple class");
    }

    @Test
    public void parseMultipleClassesAtOnce() {
        final var code = "class C { } class D { }";
        final List<ClassOrInterfaceDeclaration> types =
            new CompilationHelper(code).types();
        Assertions.assertThat(types).hasSize(2);
    }

    @Test
    public void parseAnnotatedClassesOnly() {
        final var code = "class E { } @Note class F { } class G { }";
        Assertions.assertThat(
            new CompilationHelper(code)
                .types()
                .stream()
                .filter(type -> type.isAnnotationPresent("Note"))
        ).allMatch(type -> type.getNameAsString().equals("F"));
    }

    // todo: change back to single line after Qulice fixes regex check
    // see: https://github.com/teamed/qulice/issues/975
    // and: https://github.com/teamed/qulice/issues/976
    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    public void parseJavadocOfAnnotatedClass() {
        // @checkstyle StringLiteralsConcatenation (5 lines)
        final var code = "/**"
            + " * H is annotated"
            + " */ "
            + " @Term "
            + "class H { }";
        final List<ClassOrInterfaceDeclaration> types =
            new CompilationHelper(code).types();
        Assertions.assertThat(types)
            // @checkstyle BracketsStructure (6 lines)
            .allMatch(type -> type.getJavadocComment()
                .map(JavadocComment::parse)
                .map(Javadoc::getDescription)
                .map(JavadocDescription::toText)
                .filter(description -> description.equals("H is annotated"))
                .isPresent());
    }

    /**
     * Helps compiling the code into types.
     * @since 0.1
     */
    private static class CompilationHelper {

        /**
         * The code to compile.
         */
        private final String code;

        /**
         * Main constructor.
         * @param code The code to compile
         */
        CompilationHelper(final String code) {
            this.code = code;
        }

        /**
         * Parses the code and provides types from that code.
         * @return Types from the code
         */
        private List<ClassOrInterfaceDeclaration> types() {
            return new JavaParser()
                .parse(this.code)
                .getResult()
                .orElseThrow()
                .findAll(ClassOrInterfaceDeclaration.class);
        }
    }
}
