package nl.korthout.cantis;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.equalTo;
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
        var source = new File(resourceUrl.getFile());
        sources.add(source);
    }

    @Test
    public void testWithoutFiles() {
        // Arrange
        var glossary = new Glossary(List.of());
        assertThat(glossary, is(notNullValue()));

        // Act
        final Stream<Definition> definitions = glossary.getDefinitions();

        // Assert
        assertThat(definitions, is(notNullValue()));
        assertThat(definitions.count(), is(equalTo(0L)));
    }

    @Test
    public void testOneClassFile() {
        // Arrange
        addSourceFile(classLoader.getResource("Example.java"));
        var glossary = new Glossary(sources);
        assertThat(glossary, is(notNullValue()));

        // Act
        final List<Definition> definitions = glossary.getDefinitions().collect(toList());

        // Assert
        var example = new Definition("Example", "This is a simple but great example class.");
        assertThat(definitions.size(), is(equalTo(1)));
        assertThat(definitions.get(0), is(example));
    }

    @Test
    public void testTwoClassFiles() {
        // Arrange
        addSourceFile(classLoader.getResource("Example.java"));
        addSourceFile(classLoader.getResource("Example2.java"));
        var glossary = new Glossary(sources);
        assertThat(glossary, is(notNullValue()));

        // Act
        final List<Definition> definitions = glossary.getDefinitions().collect(toList());

        // Assert
        var example = new Definition("Example", "This is a simple but great example class.");
        var example2 = new Definition("Example2", "This is another simple example class.");
        assertThat(definitions.size(), is(equalTo(2)));
        assertThat(definitions.get(0), is(example));
        assertThat(definitions.get(1), is(example2));
    }

    @Test
    public void testClassWithoutAnnotation() {
        addSourceFile(classLoader.getResource("ClassWithoutAnnotation.java"));
        var glossary = new Glossary(sources);
        assertThat(glossary, is(notNullValue()));

        // Act
        final Stream<Definition> definitions = glossary.getDefinitions();

        // Assert
        assertThat(definitions.count(), is(equalTo(0L)));
    }

    @Test
    public void testClassWithoutJavaDoc() {
        addSourceFile(classLoader.getResource("ClassWithoutJavaDoc.java"));
        var glossary = new Glossary(sources);
        assertThat(glossary, is(notNullValue()));

        // Act
        final Stream<Definition> definitions = glossary.getDefinitions();

        // Assert
        assertThat(definitions.count(), is(equalTo(0L)));
    }
}
