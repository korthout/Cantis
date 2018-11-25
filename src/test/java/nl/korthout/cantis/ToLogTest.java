package nl.korthout.cantis;

import org.apache.maven.plugin.logging.SystemStreamLog;
import org.cactoos.text.TextOf;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ToLogTest {

    private FakeLog log;

    @Before
    public void before() {
        log = new FakeLog();
    }

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNull() {
        new ToLog(null);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ConstantConditions")
    public void writeDoesNotAllowNull() {
        new ToLog(
            log
        ).write(null);
    }

    @Test
    public void everyWrittenLineEndsWithALineEnding() {
        new ToLog(
            log
        ).write(new TextOf(""));
        assertThat(
            log.lines()
        ).contains(""); // Log makes sure that lines have a line ending
    }

    @Test
    public void linesAreWrittenInOrder() {
        var to = new ToLog(log);
        to.write(new TextOf("Some Text"));
        to.write(new TextOf("Another Text"));
        to.write(new TextOf("For Good Measure"));
        assertThat(
            log.lines()
        ).containsExactly(
            "Some Text",
            "Another Text",
            "For Good Measure"
        );
    }

    /**
     * Does not log to an actual target,
     * but keeps track of the logged lines.
     */
    private final class FakeLog extends SystemStreamLog {

        private List<String> lines;

        private FakeLog() {
            lines = new ArrayList<>();
        }

        @Override
        public void info(CharSequence line) {
            lines.add(line.toString());
        }

        List<String> lines() {
            return lines;
        }
    }
}