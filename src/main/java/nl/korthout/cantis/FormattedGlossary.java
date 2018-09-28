package nl.korthout.cantis;

import org.cactoos.Text;
import org.cactoos.text.JoinedText;
import org.cactoos.text.TextOf;

import lombok.NonNull;

/**
 * Glossary with a simple line separated formatting.
 */
public final class FormattedGlossary implements Formatted {

    private final Glossary glossary;

    /**
     * Constructor.
     * @param glossary The glossary to format
     */
    FormattedGlossary(@NonNull Glossary glossary) {
        this.glossary = glossary;
    }

    @Override
    public Text formatted() {
        return glossary.definitions()
            .sorted()
            .map(Definition::text)
            .reduce((formatted, definition) -> new JoinedText(
                new TextOf(System.lineSeparator()),
                formatted, definition
            )).orElse(new TextOf("No definitions found."));
    }

}
