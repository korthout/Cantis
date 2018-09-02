package nl.korthout.cantis;

import org.cactoos.text.TextOf;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefinitionTest {

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedInConstructorAsTerm() {
        new Definition(null, "");
    }

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedInConstructorAsDefinition() {
        new Definition("", null);
    }

    // todo: remove toString() after Cactoos fixes equality of Text
    // see: https://github.com/yegor256/cactoos/issues/942
    @Test
    public void textIsATextualRepresentationOfTheDefinition() {
        assertThat(
            new Definition(
                "Glossary",
                "A list of definitions in a codebase."
            ).text().toString()
        ).isEqualTo(
            new TextOf(
                "Glossary: A list of definitions in a codebase."
            ).toString()
        );
    }

    @Test
    public void toStringIsATextualRepresentationOfTheDefinition() {
        assertThat(
            new Definition(
                "Glossary",
                "A list of definitions in a codebase."
            ).toString()
        ).isEqualTo("Glossary: A list of definitions in a codebase.");
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class)
    public void compareDoesNotAllowNull() {
        new Definition(
            "Glossary",
            "A list of definitions in a codebase."
        ).compareTo(null);
    }

    @Test
    public void definitionsCanBeCompared() {
        var author = new Definition("Author", "The originator or creator of a work.");
        var code = new Definition("Code", "Instructions for a computer.");
        assertThat(author).isLessThan(code);
        assertThat(code).isGreaterThan(author);
        assertThat(author).isEqualByComparingTo(author);
        assertThat(code).isEqualByComparingTo(code);
    }
}