package nl.korthout.cantis;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.nodeTypes.NodeWithJavadoc;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class Glossary {

    private final List<File> sources;

    public Glossary(List<File> sources) {
        this.sources = sources;
    }

    public Observable<Definition> getDefinitions() {
        return Observable.fromIterable(sources)
                .observeOn(Schedulers.io())
                .map(JavaParser::parse)
                .map(getClassesAndInterfaces())
                .flatMap(toDefinitions());
    }

    private Function<CompilationUnit, List<ClassOrInterfaceDeclaration>> getClassesAndInterfaces() {
        return compiledUnit -> compiledUnit.getChildNodesByType(ClassOrInterfaceDeclaration.class);
    }

    private Function<List<ClassOrInterfaceDeclaration>, Observable<Definition>> toDefinitions() {
        return declarations -> Observable.fromIterable(declarations)
                .filter(classOrInterface -> classOrInterface.isAnnotationPresent(GlossaryTerm.class))
                .filter(NodeWithJavadoc::hasJavaDocComment)
                .map(toDefinition());
    }

    private Function<ClassOrInterfaceDeclaration, Definition> toDefinition() {
        return classOrInterface -> new Definition(classOrInterface.getNameAsString(), getDescription(classOrInterface));
    }

    private String getDescription(ClassOrInterfaceDeclaration classOrInterface) {
        return classOrInterface.getJavadocComment()
                .orElseGet(() -> new JavadocComment("Description not found"))
                .parse()
                .getDescription()
                .toText();
    }
}
