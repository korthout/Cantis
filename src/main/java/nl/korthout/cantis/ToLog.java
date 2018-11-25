package nl.korthout.cantis;

import org.apache.maven.plugin.logging.Log;
import org.cactoos.Text;

import lombok.NonNull;
import lombok.SneakyThrows;

/**
 * Writes text lines to a Maven Log.
 */
public final class ToLog implements Destination {

    private final Log log;

    /**
     * Constructor.
     * @param log The log to write to
     */
    public ToLog(@NonNull Log log) {
        this.log = log;
    }

    @Override
    @SneakyThrows
    public void write(Text line) {
        log.info(line.asString());
    }
}
