package nl.korthout.cantis;

import org.cactoos.text.TextOf;
import org.junit.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class FormattedGlossaryTest {

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNull() {
        new FormattedGlossary(null);
    }

    // todo: remove toString() after Cactoos fixes equality of Text
    // see: https://github.com/yegor256/cactoos/issues/942
    @Test
    public void emptyGlossaryCanBeFormatted() {
        assertThat(
            new FormattedGlossary(
                new EmptyGlossary()
            ).formatted().toString()
        ).isEqualTo(
            new TextOf("No definitions found.").toString()
        );
    }

    @Test
    public void glossaryCanBeFormatted() {
        assertThat(
            new FormattedGlossary(
                new SimpleCodingGlossary()
            ).formatted().toString()
        ).isEqualTo(
            new TextOf(
                "Author: The originator or creator of a work.\n" +
                "Code: Instructions for a computer."
            ).toString()
        );
    }

    private static final class EmptyGlossary implements Glossary {

        @Override
        public Stream<Definition> definitions() {
            return Stream.empty();
        }
    }

    private static final class SimpleCodingGlossary implements Glossary {
        @Override
        public Stream<Definition> definitions() {
            return Stream.of(
                new Definition("Author", "The originator or creator of a work."),
                new Definition("Code", "Instructions for a computer.")
            );
        }
    }
}