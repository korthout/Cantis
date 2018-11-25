package nl.korthout.cantis;

import nl.korthout.cantis.fakes.FakePrintStream;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

import static org.assertj.core.api.Assertions.assertThat;

public class GenerateGlossaryIT {

    private List<File> sources;
    private ClassLoader classLoader;
    private FakePrintStream out;

    @Before
    public void initExampleSourceFiles() {
        sources = new ArrayList<>();
        classLoader = getClass().getClassLoader();
        out = new FakePrintStream();
    }

    private void addSourceFileFromResources(@NonNull String filename) {
        final URL resourceUrl = classLoader.getResource(filename);
        assertThat(resourceUrl).isNotNull();
        var source = new File(resourceUrl.getFile());
        sources.add(source);
    }

    @Test
    public void testOneClassFile() {
        addSourceFileFromResources("Example.java");
        new PrintableGlossary(
            () -> sources,
            new ToPrintStream(out)).print();
        assertThat(
            out.lines()
        ).contains(
            new Definition(
                "Example",
                "This is just a simple example class."
            ).toString() + System.lineSeparator()
        );
    }

    @Test
    public void testTwoClassFiles() {
        addSourceFileFromResources("Example.java");
        addSourceFileFromResources("Example2.java");
        new PrintableGlossary(
            () -> sources,
            new ToPrintStream(out)).print();
        assertThat(
            out.lines()
        ).contains(
            new Definition(
                "Example",
                "This is just a simple example class."
            ).toString() + System.lineSeparator(),
            new Definition(
                "Example2",
                "This is just another simple example class."
            ).toString() + System.lineSeparator()
        );
    }

}
