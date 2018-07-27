package nl.korthout.cantis;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.nodeTypes.NodeWithJavadoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import lombok.NonNull;

/**
 * A list of definitions for terms.
 */
@GlossaryTerm
public class Glossary {

    private final List<File> sources;

    public Glossary(List<File> sources) {
        this.sources = sources;
    }

    public Glossary(@NonNull SourceDirectory sourceDirectory) {
        this.sources = sourceDirectory.getFiles();
    }

    public Stream<Definition> getDefinitions() {
        System.out.println("Get definitions");
        return sources.stream()
                .map(this::parseFile)
                .map(getClassesAndInterfaces())
                .flatMap(toDefinitions());
    }

    private CompilationUnit parseFile(File file) {
        try {
            return JavaParser.parse(file);
        } catch (FileNotFoundException e) {
            // TODO: do something better than return null
            return null;
        }
    }

    private Function<CompilationUnit, List<ClassOrInterfaceDeclaration>> getClassesAndInterfaces() {
        return compiledUnit -> compiledUnit.getChildNodesByType(ClassOrInterfaceDeclaration.class);
    }

    private Function<List<ClassOrInterfaceDeclaration>, Stream<Definition>> toDefinitions() {
        return classesAndInterfaces -> classesAndInterfaces.stream()
                .filter(classOrInterface -> classOrInterface.isAnnotationPresent(GlossaryTerm.class))
                .filter(NodeWithJavadoc::hasJavaDocComment)
                .map(toDefinition());
    }

    private Function<ClassOrInterfaceDeclaration, Definition> toDefinition() {
        return classOrInterface -> {
            var term = classOrInterface.getNameAsString();
            var description = getDescription(classOrInterface);
            return new Definition(term, description);
        };
    }

    private String getDescription(ClassOrInterfaceDeclaration classOrInterface) {
        return classOrInterface.getJavadocComment()
                .orElseGet(() -> new JavadocComment("Description not found"))
                .parse()
                .getDescription()
                .toText();
    }
}
