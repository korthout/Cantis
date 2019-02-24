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

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.ast.nodeTypes.NodeWithJavadoc;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import com.github.korthout.cantis.Type.TypeFromJavaparser;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Unit tests for {@code TypeFromJavaparser} objects.
 * @since 0.1.1
 */
@SuppressWarnings("PMD.ProhibitPlainJunitAssertionsRule")
public class TypeFromJavaparserTest {

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNullAnnotated() {
        new Type.TypeFromJavaparser(
            null,
            new FakeNodeWithJavadoc(),
            new FakeNodeWithSimpleName()
        );
    }

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNullDocumented() {
        new Type.TypeFromJavaparser(
            new FakeNodeWithAnnotations(),
            null,
            new FakeNodeWithSimpleName()
        );
    }

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNullNamed() {
        new Type.TypeFromJavaparser(
            new FakeNodeWithAnnotations(),
            new FakeNodeWithJavadoc(),
            null
        );
    }

    @Test
    public void typeCanHaveJavadoc() {
        Assertions.assertThat(
            new TypeFromJavaparser(
                new FakeNodeWithAnnotations(),
                new FakeNodeWithJavadoc(),
                new FakeNodeWithSimpleName()
            ).hasJavadoc()
        ).isTrue();
    }

    @Test
    public void typeDoesNotRequireJavadoc() {
        Assertions.assertThat(
            new Type.TypeFromJavaparser(
                new FakeNodeWithAnnotations(),
                new FakeNodeWithoutJavadoc(),
                new FakeNodeWithSimpleName()
            ).hasJavadoc()
        ).isFalse();
    }

    @Test
    public void typeCanHaveAnnotations() {
        Assertions.assertThat(
            new Type.TypeFromJavaparser(
                new FakeNodeWithAnnotations(),
                new FakeNodeWithJavadoc(),
                new FakeNodeWithSimpleName()
            ).hasGlossaryTermAnnotation()
        ).isTrue();
    }

    @Test
    public void typeDoNotRequireAnnotations() {
        Assertions.assertThat(
            new Type.TypeFromJavaparser(
                new FakeNodeWithoutAnnotations(),
                new FakeNodeWithJavadoc(),
                new FakeNodeWithSimpleName()
            ).hasGlossaryTermAnnotation()
        ).isFalse();
    }

    @Test
    public void typeCanBeDescribedByADefinition() {
        final var description = "Acts as a type with Javadoc.";
        final var name = "FakeType";
        Assertions.assertThat(
            new TypeFromJavaparser(
                new FakeNodeWithAnnotations(),
                new FakeNodeWithJavadoc(description),
                new FakeNodeWithSimpleName(name)
            ).definition()
        ).isEqualTo(
            new Definition(name, description)
        );
    }

    /**
     * Node is annotated with @GlossaryTerm.
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    private final class FakeNodeWithAnnotations
        implements NodeWithAnnotations<Node> {

        @Override
        public NodeList<AnnotationExpr> getAnnotations() {
            return new NodeList<>(
                new MarkerAnnotationExpr("GlossaryTerm")
            );
        }

        @Override
        public void tryAddImportToParentCompilationUnit(final Class clazz) {
            // not necessary to implement
        }

        @Override
        public Node setAnnotations(final NodeList annotations) {
            return null;
        }
    }

    /**
     * Node is not annotated.
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    private final class FakeNodeWithoutAnnotations
        implements NodeWithAnnotations<Node> {

        @Override
        public NodeList<AnnotationExpr> getAnnotations() {
            return new NodeList<>();
        }

        @Override
        public void tryAddImportToParentCompilationUnit(final Class clazz) {
            // not necessary to implement
        }

        @Override
        public Node setAnnotations(final NodeList annotations) {
            return null;
        }
    }

    /**
     * Node has Javadoc description: Acts as a type with Javadoc.
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    private final class FakeNodeWithJavadoc
        implements NodeWithJavadoc<TypeDeclaration> {

        /**
         * The Javadoc description of this fake type.
         */
        private final String description;

        private FakeNodeWithJavadoc(final String description) {
            this.description = description;
        }

        private FakeNodeWithJavadoc() {
            this("");
        }

        @Override
        public Optional<Comment> getComment() {
            return Optional.of(new JavadocComment(this.description));
        }

        @Override
        public Node setComment(final Comment comment) {
            return null;
        }
    }

    /**
     * Node does not have a Javadoc description.
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    private final class FakeNodeWithoutJavadoc
        implements NodeWithJavadoc<TypeDeclaration> {

        @Override
        public Optional<Comment> getComment() {
            return Optional.empty();
        }

        @Override
        public Node setComment(final Comment comment) {
            return null;
        }
    }

    /**
     * Node has the name 'FakeType'.
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    private final class FakeNodeWithSimpleName implements NodeWithSimpleName {

        /**
         * Tha name of this fake node object.
         */
        private final String name;

        private FakeNodeWithSimpleName(final String name) {
            this.name = name;
        }

        private FakeNodeWithSimpleName() {
            this("Simple");
        }

        @Override
        public SimpleName getName() {
            return new SimpleName(this.name);
        }

        @Override
        // @checkstyle HiddenField (1 lines)
        public Node setName(final SimpleName name) {
            return null;
        }
    }
}
