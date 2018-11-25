package nl.korthout.cantis;

import nl.korthout.cantis.fakes.FakePrintStream;

import org.cactoos.text.TextOf;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ToPrintStreamTest {

    private FakePrintStream out;

    @Before
    public void before() {
        out = new FakePrintStream();
    }

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNull() {
        new ToPrintStream(null);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ConstantConditions")
    public void writeDoesNotAllowNull() {
        new ToPrintStream(
            new FakePrintStream()
        ).write(null);
    }

    @Test
    public void everyWrittenLineEndsWithALineEnding() {
        new ToPrintStream(
            out
        ).write(new TextOf(""));
        assertThat(
            out.lines()
        ).contains(System.lineSeparator());
    }

    @Test
    public void linesAreWrittenInOrder() {
        var to = new ToPrintStream(out);
        to.write(new TextOf("Some Text"));
        to.write(new TextOf("Another Text"));
        to.write(new TextOf("For Good Measure"));
        assertThat(
            out.lines()
        ).containsExactly(
            "Some Text" + System.lineSeparator(),
            "Another Text" + System.lineSeparator(),
            "For Good Measure" + System.lineSeparator()
        );
    }
}