package nl.korthout.cantis;

import org.cactoos.Text;

/**
 * Formatted {@link Text} representation of an object.
 */
public interface Formatted {

    /**
     * Provides a text representation of this.
     * @return The formatted text.
     */
    Text formatted();
}
