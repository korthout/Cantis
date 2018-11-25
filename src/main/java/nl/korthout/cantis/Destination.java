package nl.korthout.cantis;

import org.cactoos.Text;

/**
 * Somewhere to write output text lines to.
 */
public interface Destination {

    /**
     * Write a line to the destination.
     * @param line The line to write
     */
    void write(Text line);
}
