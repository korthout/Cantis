package nl.korthout.cantis;

import org.cactoos.list.ListOf;
import org.junit.Test;

import java.util.stream.Stream;

import lombok.NonNull;

import static org.assertj.core.api.Assertions.assertThat;

public class GlossaryTest {

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNull() {
        new CodebaseGlossary((Stream<Classifier>) null);
    }

    @Test
    public void anEmptyListOfClassifiersHasNoDefinitions() {
        assertThat(
            new CodebaseGlossary(
                new ListOf<Classifier>().stream()
            ).definitions()
        ).isEmpty();
    }

    @Test
    public void anEmptyCodebaseHasNoDefinitions() {
        assertThat(
            new CodebaseGlossary(
                new EmptyCodebase()
            ).definitions()
        ).isEmpty();
    }

    @Test
    public void codebaseOfClassifierWithGlossaryTermAnnotationAndJavadocHasDefinitions() {
        assertThat(
            new CodebaseGlossary(
                new ListOf<Classifier>(
                    new FakeClassifier("FakeClassifier", "Acts like a Classifier.")
                ).stream()
            ).definitions()
        ).containsExactlyInAnyOrder(
            new Definition("FakeClassifier", "Acts like a Classifier.")
        );
    }

    @Test
    public void codebaseWithClassifierWithoutGlossaryTermAnnotationHasNoDefinitions() {
        assertThat(
            new CodebaseGlossary(
                new ListOf<Classifier>(
                    new FakeClassifierWithoutAnnotation()
                ).stream()
            ).definitions()
        ).isEmpty();
    }

    @Test
    public void codebaseWithClassifierWithoutJavadocHasNoDefinitions() {
        assertThat(
            new CodebaseGlossary(
                new ListOf<Classifier>(
                    new FakeClassifierWithoutJavadoc()
                ).stream()
            ).definitions()
        ).isEmpty();
    }

    @Test
    public void codebaseWithDifferentClassifiersHasDefinitionsForTheRightClassifiers() {
        assertThat(
            new CodebaseGlossary(
                new ListOf<>(
                    new FakeClassifierWithoutJavadoc(),
                    new FakeClassifier("FakeClassifier1", "Acts like a Classifier."),
                    new FakeClassifierWithoutAnnotation(),
                    new FakeClassifier("FakeClassifier2", "Also acts like a Classifier."),
                    new FakeClassifierWithoutAnnotation()
                ).stream()
            ).definitions()
        ).containsExactlyInAnyOrder(
            new Definition("FakeClassifier1", "Acts like a Classifier."),
            new Definition("FakeClassifier2", "Also acts like a Classifier.")
        );
    }

    private static final class EmptyCodebase implements Codebase {

        @Override
        public Stream<Classifier> classifiers() {
            return Stream.empty();
        }
    }

    private static final class FakeClassifier implements Classifier {

        private final String name;
        private final String javadoc;

        FakeClassifier(@NonNull String name, @NonNull String javadoc) {
            this.name = name;
            this.javadoc = javadoc;
        }

        @Override
        public boolean hasGlossaryTermAnnotation() {
            return true;
        }

        @Override
        public boolean hasJavadoc() {
            return true;
        }

        @Override
        public Definition definition() {
            return new Definition(name, javadoc);
        }
    }

    private static final class FakeClassifierWithoutAnnotation implements Classifier {

        @Override
        public boolean hasGlossaryTermAnnotation() {
            return false;
        }

        @Override
        public boolean hasJavadoc() {
            return true;
        }

        @Override
        public Definition definition() {
            return null;
        }
    }

    private static final class FakeClassifierWithoutJavadoc implements Classifier {

        @Override
        public boolean hasGlossaryTermAnnotation() {
            return true;
        }

        @Override
        public boolean hasJavadoc() {
            return false;
        }

        @Override
        public Definition definition() {
            return null;
        }
    }
}
