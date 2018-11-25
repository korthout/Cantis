package nl.korthout.cantis;

import org.cactoos.Text;

import java.io.PrintStream;

import lombok.NonNull;
import lombok.SneakyThrows;

/**
 * Writes text lines to a PrintStream.
 */
public final class ToPrintStream implements Destination {

    private final PrintStream out;

    /**
     * Constructor.
     * @param out The PrintStream to write to
     */
    public ToPrintStream(@NonNull PrintStream out) {
        this.out = out;
    }

    @Override
    @SneakyThrows
    public void write(@NonNull Text line) {
        out.println(line.asString());
    }
}
