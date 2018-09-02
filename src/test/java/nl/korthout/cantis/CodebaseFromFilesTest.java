package nl.korthout.cantis;

import nl.korthout.cantis.Codebase.CodebaseFromFiles;

import org.cactoos.list.ListOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Predicate;

import lombok.NonNull;

import static org.assertj.core.api.Assertions.assertThat;

public class CodebaseFromFilesTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ConstantConditions")
    public void nullIsNotAllowedAsDirectoryInConstructor() {
        new CodebaseFromFiles((Directory) null);
    }

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedInMainConstructor() {
        new CodebaseFromFiles(() -> null);
    }

    @Test
    public void codebaseOfNoSourcesHasNoClassifiers() {
        assertThat(
            new CodebaseFromFiles(
                new ListOf<>()
            ).classifiers()
        ).isEmpty();
    }

    @Test
    public void codebaseCanBeConstructedFromDirectory() {
        assertThat(
            new CodebaseFromFiles(
                ListOf<File>::new
            ).classifiers()
        ).isEmpty();
    }

    @Test
    public void codebaseOfTextFileHasNoClassifiers() {
        assertThat(
            new CodebaseFromFiles(
                new ListOf<>(
                    new FakeFile("TextFile.txt").fileWithContent("Just a simple text file, with some text.")
                )
            ).classifiers()
        ).isEmpty();
    }

    @Test
    public void codebaseOfJavaFileHasAClassifier() {
        assertThat(
            new CodebaseFromFiles(
                new ListOf<>(
                    new FakeFile("SimpleClass.java").fileWithContent("class SimpleClass { }")
                )
            ).classifiers()
        ).hasSize(1)
            .allMatch(new NoAnnotation())
            .allMatch(new NoJavadoc())
            .allMatch(new HasDefinition(
                new Definition("SimpleClass", "Description not found")
            ));
    }

    @Test
    public void codebaseOfAnnotatedJavaFileHasClassifierWithAnnotation() {
        assertThat(
            new CodebaseFromFiles(
                new ListOf<>(
                    new FakeFile("AnnotatedClass.java")
                        .fileWithContent("@GlossaryTerm class AnnotatedClass { }")
                )
            ).classifiers()
        ).hasSize(1)
            .allMatch(new HasAnnotation())
            .allMatch(new NoJavadoc())
            .allMatch(new HasDefinition(
                new Definition("AnnotatedClass", "Description not found")
            ));
    }

    @Test
    public void codebaseOfJavaFileWithJavadocHasClassifierWithJavadoc() {
        assertThat(
            new CodebaseFromFiles(
                new ListOf<>(
                    new FakeFile("ClassWithJavadoc.java")
                        .fileWithContent("/** Some documentation */ class ClassWithJavadoc { }")
                )
            ).classifiers()
        ).hasSize(1)
            .allMatch(new HasJavadoc())
            .allMatch(new NoAnnotation())
            .allMatch(new HasDefinition(
                new Definition("ClassWithJavadoc", "Some documentation")
            ));
    }

    @Test
    public void codebaseCanContainManyClassifiers() {
        assertThat(
            new CodebaseFromFiles(
                new ListOf<>(
                    new FakeFile("SimpleClass.java")
                        .fileWithContent("class SimpleClass { }"),
                    new FakeFile("AnnotatedClass.java")
                        .fileWithContent("@GlossaryTerm class AnnotatedClass { }"),
                    new FakeFile("ClassWithJavadoc.java")
                        .fileWithContent("/** Some documentation */ class ClassWithJavadoc { }")
                )
            ).classifiers()
        ).hasSize(3);
    }

    // todo: write more codebase tests

    private final class FakeFile {

        private final File file;

        FakeFile(@NonNull String name) {
            try {
                file = tempFolder.newFile(name);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }

        File fileWithContent(@NonNull String content) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
                writer.flush();
                return file;
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    private class NoAnnotation implements Predicate<Classifier> {

        @Override
        public boolean test(Classifier classifier) {
            return !classifier.hasGlossaryTermAnnotation();
        }
    }

    private class HasAnnotation implements Predicate<Classifier> {

        @Override
        public boolean test(Classifier classifier) {
            return classifier.hasGlossaryTermAnnotation();
        }
    }

    private class NoJavadoc implements Predicate<Classifier> {

        @Override
        public boolean test(Classifier classifier) {
            return !classifier.hasJavadoc();
        }
    }

    private class HasJavadoc implements Predicate<Classifier> {

        @Override
        public boolean test(Classifier classifier) {
            return classifier.hasJavadoc();
        }
    }

    private class HasDefinition implements Predicate<Classifier> {

        private final Definition definition;

        HasDefinition(@NonNull Definition definition) {
            this.definition = definition;
        }

        @Override
        public boolean test(Classifier classifier) {
            return definition.equals(classifier.definition());
        }
    }
}