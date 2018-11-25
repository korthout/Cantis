package nl.korthout.cantis;

import org.cactoos.Text;
import org.cactoos.list.ListOf;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.SneakyThrows;

import static org.assertj.core.api.Assertions.assertThat;

public class PrintableGlossaryTest {

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedAsDirectory() {
        new PrintableGlossary(null, new FakeDestination());
    }

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedAsOutput() {
        new PrintableGlossary(ListOf<File>::new, null);
    }

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedAsInfo() {
        new PrintableGlossary(ListOf<File>::new, null, new FakeDestination());
    }

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedAsTarget() {
        new PrintableGlossary(ListOf<File>::new, new FakeDestination(), null);
    }

    @Test
    public void printsNumberOfFiles() {
        var info = new FakeDestination();
        new PrintableGlossary(
            ListOf<File>::new,
            info,
            new FakeDestination()
        ).print();
        assertThat(
            info.lines()
        ).contains(
            "Scanning 0 java source files for @GlossaryTerm annotation" + System.lineSeparator()
        );
    }

    @Test
    public void printsGlossaryToDestination() {
        var destination = new FakeDestination();
        new PrintableGlossary(
            ListOf<File>::new,
            new FakeDestination(),
            destination
        ).print();
        assertThat(
            destination.lines()
        ).contains(
            "No definitions found." + System.lineSeparator()
        );
    }

    @Test
    public void printsFinished() {
        var info = new FakeDestination();
        new PrintableGlossary(
            ListOf<File>::new,
            info,
            new FakeDestination()
        ).print();
        assertThat(
            info.lines()
        ).contains(
            "Finished in: 0s" + System.lineSeparator()
        );
    }

    class FakeDestination implements Destination {

        private List<String> lines = new ArrayList<>();

        @Override
        @SneakyThrows
        public void write(Text output) {
            lines.add(output.asString() + System.lineSeparator());
        }

        List<String> lines() {
            return lines;
        }
    }

}