package nl.korthout.cantis;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseStart;
import com.github.javaparser.Providers;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import nl.korthout.cantis.Classifier.ClassifierFromJavaparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

import lombok.NonNull;

/**
 * A collection of code that forms a program.
 */
public interface Codebase {

    /**
     * Provides representations of the interfaces
     * and classes in this in codebase.
     * @return The classifiers in this codebase
     */
    Stream<Classifier> classifiers();

    /**
     * A collection of code constructed from source files.
     */
    final class CodebaseFromFiles implements Codebase {

        private final Collection<File> sources;
        private final JavaParser javaParser;

        /**
         * Constructor.
         * @param directory The root directory containing the source files
         */
        public CodebaseFromFiles(Directory directory) {
            this(directory.files());
        }

        /**
         * Constructor.
         * @param sources The sources that form this codebase
         */
        public CodebaseFromFiles(@NonNull Collection<File> sources) {
            this.sources = sources;
            this.javaParser = new JavaParser();
        }

        @Override
        public Stream<Classifier> classifiers() {
            return sources.stream()
                .map(this::parse)
                .flatMap(Optional::stream)
                .map(compilationUnit -> compilationUnit.findAll(ClassOrInterfaceDeclaration.class))
                .flatMap(declarations -> declarations.stream().map(ClassifierFromJavaparser::new));
        }

        private Optional<CompilationUnit> parse(File file) {
            try {
                return javaParser.parse(
                    ParseStart.COMPILATION_UNIT,
                    Providers.provider(file)
                ).getResult();
            } catch (FileNotFoundException e) {
                return Optional.empty();
            }
        }
    }
}
