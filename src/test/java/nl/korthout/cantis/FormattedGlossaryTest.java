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

    @Test
    public void emptyGlossaryCanBeFormatted() {
        assertThat(
            new FormattedGlossary(
                new EmptyGlossary()
            ).formatted()
        ).isEqualTo(
            new TextOf("No definitions found.")
        );
    }

    @Test
    public void glossaryCanBeFormatted() {
        assertThat(
            new FormattedGlossary(
                new SimpleCodingGlossary()
            ).formatted()
        ).isEqualTo(
            new TextOf(
                "Author: The originator or creator of a work.\n" +
                "Code: Instructions for a computer."
            )
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