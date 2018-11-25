package nl.korthout.cantis;

import java.util.stream.Stream;

/**
 * A list of definitions.
 */
@GlossaryTerm
public interface Glossary {

    /**
     * Builds definitions for this glossary.
     * @return The definitions.
     */
    Stream<Definition> definitions();
}
