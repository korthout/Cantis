package nl.korthout.cantis;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import io.reactivex.observers.TestObserver;
import lombok.val;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class GlossaryTest {

    private List<File> sources;
    private ClassLoader classLoader;

    @Before
    public void initExampleSourceFiles() {
        sources = new ArrayList<>();
        classLoader = getClass().getClassLoader();
    }

    private void addSourceFile(@Nullable URL resourceUrl) {
        assertThat(resourceUrl, is(notNullValue()));
        val source = new File(resourceUrl.getFile());
        sources.add(source);
    }

    @Test
    public void testWithoutFiles() {
        // Arrange
        val glossary = new Glossary(List.of());
        assertThat(glossary, is(notNullValue()));

        // Act
        final TestObserver<Definition> observer = glossary.getDefinitions().test();

        // Assert
        observer.awaitTerminalEvent();
        observer.assertNoErrors()
                .assertNoValues()
                .assertComplete();
    }

    @Test
    public void testOneClassFile() {
        // Arrange
        addSourceFile(classLoader.getResource("Example.java"));
        val glossary = new Glossary(sources);
        assertThat(glossary, is(notNullValue()));

        // Act
        final TestObserver<Definition> observer = glossary.getDefinitions().test();

        // Assert
        observer.awaitTerminalEvent();
        observer.assertNoErrors()
                .assertValueCount(1)
                .assertValue(new Definition("Example", "This is a simple but great example class."))
                .assertComplete();
    }

    @Test
    public void testTwoClassFiles() {
        // Arrange
        addSourceFile(classLoader.getResource("Example.java"));
        addSourceFile(classLoader.getResource("Example2.java"));
        val glossary = new Glossary(sources);
        assertThat(glossary, is(notNullValue()));

        // Act
        final TestObserver<Definition> observer = glossary.getDefinitions().test();

        // Assert
        observer.awaitTerminalEvent();
        observer.assertNoErrors()
                .assertValueCount(2)
                .assertValues(
                        new Definition("Example", "This is a simple but great example class."),
                        new Definition("Example2", "This is another simple example class.")
                ).assertComplete();
    }

    @Test
    public void testClassWithoutAnnotation() {
        addSourceFile(classLoader.getResource("ClassWithoutAnnotation.java"));
        val glossary = new Glossary(sources);
        assertThat(glossary, is(notNullValue()));

        // Act
        final TestObserver<Definition> observer = glossary.getDefinitions().test();

        // Assert
        observer.awaitTerminalEvent();
        observer.assertNoErrors()
                .assertNoValues()
                .assertComplete();
    }

    @Test
    public void testClassWithoutJavaDoc() {
        addSourceFile(classLoader.getResource("ClassWithoutJavaDoc.java"));
        val glossary = new Glossary(sources);
        assertThat(glossary, is(notNullValue()));

        // Act
        final TestObserver<Definition> observer = glossary.getDefinitions().test();

        // Assert
        observer.awaitTerminalEvent();
        observer.assertNoErrors()
                .assertNoValues()
                .assertComplete();
    }
}
