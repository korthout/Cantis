package com.github.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.nodeTypes.NodeWithJavadoc;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import com.github.javaparser.javadoc.Javadoc;
import com.github.javaparser.javadoc.description.JavadocDescription;

import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Exploratory tests for the Java Parser library.
 * To try-out and document the usage of this library.
 */
public class JavaParserExploratoryTests {

    private static final String GLOSSARY_TERM = "GlossaryTerm";

    @Test
    public void parseSimpleClass() {
        var code = "class A { }";
        CompilationUnit compilationUnit = JavaParser.parse(code);
        Optional<ClassOrInterfaceDeclaration> classA = compilationUnit.getClassByName("A");
        assertThat(classA).map(NodeWithSimpleName::getNameAsString).contains("A");
    }

    @Test
    public void parseClassWithJavadocComment() {
        var code = "/** B is a simple class */ class B { }";
        Optional<JavadocComment> javadocComment = JavaParser.parse(code)
            .getClassByName("B")
            .flatMap(NodeWithJavadoc::getJavadocComment);
        assertThat(javadocComment)
            .map(JavadocComment::parse)
            .map(Javadoc::getDescription)
            .map(JavadocDescription::toText)
            .contains("B is a simple class");
    }

    @Test
    public void parseMultipleClassesAtOnce() {
        var code = "class C { } class D { }";
        List<ClassOrInterfaceDeclaration> classesAndInterfaces = CompilationHelper.compileClassesAndInterfaces(code);
        assertThat(classesAndInterfaces).hasSize(2);
    }

    @Test
    public void parseAnnotatedClassesOnly() {
        var code = "class E { } @GlossaryTerm class F { } class G { }";
        List<ClassOrInterfaceDeclaration> classesAndInterfaces = CompilationHelper.compileClassesAndInterfaces(code);
        assertThat(classesAndInterfaces)
            .filteredOn(classOrInterface -> classOrInterface.isAnnotationPresent(GLOSSARY_TERM))
            .hasSize(1)
            .allSatisfy(classOrInterface -> assertThat(classOrInterface.getNameAsString()).isEqualTo("F"));
    }

    @Test
    public void parseJavadocOfAnnotatedClass() {
        var code = "/** H is an annotated class */ @GlossaryTerm class H { }";
        List<ClassOrInterfaceDeclaration> classesAndInterfaces = CompilationHelper.compileClassesAndInterfaces(code);
        assertThat(classesAndInterfaces)
            .allSatisfy(classOrInterface -> assertThat(classOrInterface.getJavadocComment())
                .map(JavadocComment::parse)
                .map(Javadoc::getDescription)
                .map(JavadocDescription::toText)
                .contains("H is an annotated class"));
    }

    private static class CompilationHelper {
        private static List<ClassOrInterfaceDeclaration> compileClassesAndInterfaces(String code) {
            CompilationUnit compiledCode = JavaParser.parse(code);
            return compiledCode.findAll(ClassOrInterfaceDeclaration.class);
        }
    }
}
