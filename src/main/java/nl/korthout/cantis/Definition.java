package nl.korthout.cantis;

import org.cactoos.Text;
import org.cactoos.text.TextOf;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * A term and its description.
 */
@GlossaryTerm
@EqualsAndHashCode
public final class Definition implements Comparable<Definition> {

    private final String term;
    private final String description;

    /**
     * Constructor.
     * @param term The term that is defined
     * @param description The term's description
     */
    public Definition(@NonNull String term, @NonNull String description) {
        this.term = term;
        this.description = description;
    }

    @Override
    public int compareTo(@NonNull Definition other) {
        return term.compareTo(other.term);
    }

    /**
     * Builds a textual representation of this definition.
     * @return The definition as {@link Text}
     */
    public Text text() {
        return new TextOf(term + ": " + description);
    }

    /**
     * Builds a textual representation of this definition.
     * @return The definition as {@link String}.
     */
    @Override
    public String toString() {
        return text().toString();
    }
}
