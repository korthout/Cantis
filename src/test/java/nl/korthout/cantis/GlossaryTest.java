package nl.korthout.cantis;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class GlossaryTest {

    private List<File> sources;
    private ClassLoader classLoader;

    @Before
    public void initExampleSourceFiles() {
        sources = new ArrayList<>();
        classLoader = getClass().getClassLoader();
    }

    private void addSourceFileFromResources(String filename) {
        final URL resourceUrl = classLoader.getResource(filename);
        assertThat(resourceUrl).isNotNull();
        var source = new File(resourceUrl.getFile());
        sources.add(source);
    }

    @Test
    public void testWithoutFiles() {
        var glossary = new Glossary(List.of());
        Stream<Definition> definitions = glossary.getDefinitions();
        assertThat(definitions).isEmpty();
    }

    @Test
    public void testOneClassFile() {
        addSourceFileFromResources("Example.java");
        var glossary = new Glossary(sources);
        List<Definition> definitions = glossary.getDefinitions().collect(toList());
        var example = new Definition("Example", "This is a simple but great example class.");
        assertThat(definitions).containsOnly(example);
    }

    @Test
    public void testTwoClassFiles() {
        addSourceFileFromResources("Example.java");
        addSourceFileFromResources("Example2.java");
        var glossary = new Glossary(sources);
        List<Definition> definitions = glossary.getDefinitions().collect(toList());
        assertThat(definitions).containsExactlyInAnyOrder(
                new Definition("Example", "This is a simple but great example class."),
                new Definition("Example2", "This is another simple example class.")
        );
    }

    @Test
    public void testClassWithoutAnnotation() {
        addSourceFileFromResources("ClassWithoutAnnotation.java");
        var glossary = new Glossary(sources);
        Stream<Definition> definitions = glossary.getDefinitions();
        assertThat(definitions).isEmpty();
    }

    @Test
    public void testClassWithoutJavaDoc() {
        addSourceFileFromResources("ClassWithoutJavaDoc.java");
        var glossary = new Glossary(sources);
        Stream<Definition> definitions = glossary.getDefinitions();
        assertThat(definitions).isEmpty();
    }
}
