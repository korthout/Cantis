package nl.korthout.cantis;

import nl.korthout.cantis.fakes.FakeOutputStream;

import org.cactoos.list.ListOf;
import org.junit.Test;

import java.io.File;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;

public class PrintableGlossaryTest {

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedAsDirectory() {
        new PrintableGlossary(null, new PrintStream(new FakeOutputStream()));
    }

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedAsOut() {
        new PrintableGlossary(ListOf<File>::new, null);
    }

    @Test
    public void printsNumberOfFiles() {
        final FakeOutputStream out = new FakeOutputStream();
        new PrintableGlossary(
            ListOf<File>::new,
            new PrintStream(out)
        ).print();
        assertThat(
            out.lines()
        ).contains(
            "Scanning 0 java source files for @GlossaryTerm annotation" + System.lineSeparator()
        );
    }

    @Test
    public void printsGlossary() {
        final FakeOutputStream out = new FakeOutputStream();
        new PrintableGlossary(
            ListOf<File>::new,
            new PrintStream(out)
        ).print();
        assertThat(
            out.lines()
        ).contains(
            "No definitions found." + System.lineSeparator()
        );
    }

    @Test
    public void printsFinished() {
        final FakeOutputStream out = new FakeOutputStream();
        new PrintableGlossary(
            ListOf<File>::new,
            new PrintStream(out)
        ).print();
        assertThat(
            out.lines()
        ).contains(
            "Finished in: 0s" + System.lineSeparator()
        );
    }

}