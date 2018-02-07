package com.github.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.nodeTypes.NodeWithJavadoc;
import com.github.javaparser.javadoc.Javadoc;

import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Exploratory tests for the Java Parser library.
 * To try-out and document the usage of this library.
 */
public class JavaParserExploratoryTests {

    private static final String GLOSSARY = "GlossaryTerm";

    @Test
    public void parseSimpleClass() {
        final String code = "class A { }";
        final CompilationUnit compilationUnit = JavaParser.parse(code);
        final Optional<ClassOrInterfaceDeclaration> classA = compilationUnit.getClassByName("A");
        assertThat(classA.isPresent(), is(Boolean.TRUE));
        assertThat(classA.get().getNameAsString(), is("A"));
    }

    @Test
    public void parseClassWithJavadocComment() {
        final String code = "/** B is a simple class */ class B { }";
        final Optional<JavadocComment> javadocComment = JavaParser.parse(code)
                .getClassByName("B")
                .flatMap(NodeWithJavadoc::getJavadocComment);
        assertThat(javadocComment.isPresent(), is(Boolean.TRUE));
        final Javadoc javadoc = javadocComment.get().parse();
        assertThat(javadoc.getDescription().toText(), is("B is a simple class"));
    }

    @Test
    public void parseAnnotatedClassesOnly() {
        final String code = "class C { } @GlossaryTerm class D { } class E { }";
        final CompilationUnit compilationUnit = JavaParser.parse(code);
        assertThat(compilationUnit.getAnnotationDeclarationByName(GLOSSARY), is(notNullValue()));
        compilationUnit.getChildNodesByType(ClassOrInterfaceDeclaration.class)
                .stream()
                .filter(classOrInterface -> classOrInterface.isAnnotationPresent(GLOSSARY))
                .forEach(classOrInterface -> assertThat(classOrInterface.getNameAsString(), is("D")));
    }

    @Test
    public void parseJavadocOfAnnotatedClass() {
        final String code = "/** F is an annotated class */ @GlossaryTerm class F { }";
        JavaParser.parse(code)
                .getChildNodesByType(ClassOrInterfaceDeclaration.class)
                .stream()
                .peek(classOrInterface -> classOrInterface.isAnnotationPresent(GLOSSARY))
                .map(ClassOrInterfaceDeclaration::getJavadocComment)
                .peek(javadocComment -> assertThat(javadocComment.isPresent(), is(Boolean.TRUE)))
                .map(javadocComment -> javadocComment.get().parse().getDescription().toText())
                .forEach(description -> assertThat(description, is("F is an annotated class")));
    }
}
