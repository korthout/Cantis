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

import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.ast.nodeTypes.NodeWithJavadoc;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import lombok.NonNull;

/**
 * A type in a Java program as declared in a .java file.
 * @since 0.1.1
 */
@Term
public interface Type {

    /**
     * Tells whether this Type is annotated as glossary term.
     * @return True when this is annotated with {@code Term}
     */
    boolean hasGlossaryTermAnnotation();

    /**
     * Tells whether this Type has a Javadoc description.
     * @return True when this has a Javadoc comment at the type level
     */
    boolean hasJavadoc();

    /**
     * Builds a {@code Definition} from this type.
     * @return The definition
     */
    Definition definition();

    /**
     * Type that is created using the com.github.Javaparser library.
     * @since 0.1.1
     */
    final class TypeFromJavaparser implements Type {

        /**
         * The annotated part of the type.
         */
        private final NodeWithAnnotations annotated;

        /**
         * The documented part of the type.
         */
        private final NodeWithJavadoc<TypeDeclaration> documented;

        /**
         * The named part of the type.
         */
        private final NodeWithSimpleName named;

        /**
         * Main Constructor.
         * @param note The annotated part of the type
         * @param doc The documented part of the type
         * @param name The named part of the type
         */
        public TypeFromJavaparser(
            final @NonNull NodeWithAnnotations note,
            final @NonNull NodeWithJavadoc<TypeDeclaration> doc,
            final @NonNull NodeWithSimpleName name
        ) {
            this.annotated = note;
            this.documented = doc;
            this.named = name;
        }

        /**
         * Constructor.
         * @param declaration This type's declaration
         */
        public TypeFromJavaparser(final TypeDeclaration declaration) {
            this(declaration, declaration, declaration);
        }

        @Override
        public boolean hasGlossaryTermAnnotation() {
            return this.annotated.isAnnotationPresent(
                Term.class.getSimpleName()
            );
        }

        @Override
        public boolean hasJavadoc() {
            return this.documented.hasJavaDocComment();
        }

        @Override
        public Definition definition() {
            final var term = this.named.getNameAsString();
            final var description = this.documented.getJavadocComment()
                .orElseGet(() -> new JavadocComment("Description not found"))
                .parse().getDescription().toText();
            return new Definition(term, description);
        }
    }
}
