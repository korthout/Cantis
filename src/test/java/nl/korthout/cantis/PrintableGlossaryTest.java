package nl.korthout.cantis;

import org.cactoos.list.ListOf;
import org.junit.Test;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

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

    private class FakeOutputStream extends OutputStream {

        private List<String> written;
        private String line;

        FakeOutputStream() {
            this.written = new ArrayList<>();
            this.line = "";
        }

        @Override
        public void write(int byteToWrite) {
            final String charToWrite = String.valueOf((char) byteToWrite);
            line = line.concat(charToWrite);
            if (charToWrite.equals(System.lineSeparator())) {
                written.add(line);
                line = "";
            }
        }

        List<String> lines() {
            return written;
        }
    }
}