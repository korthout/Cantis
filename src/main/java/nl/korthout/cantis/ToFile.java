package nl.korthout.cantis;

import org.cactoos.Text;

import java.io.File;
import java.nio.file.Files;

import lombok.NonNull;
import lombok.SneakyThrows;

/**
 * Writes text lines to a File.
 */
public final class ToFile implements Destination {

    private final File file;

    /**
     * Constructor.
     * @param path Path to the file
     */
    public ToFile(String path) {
        this(new File(path));
    }

    /**
     * Constructor.
     * @param file File to write to
     */
    public ToFile(@NonNull File file) {
        this.file = file;
    }

    @Override
    @SneakyThrows
    public void write(@NonNull Text line) {
        Files.writeString(
            file.toPath(),
            line.asString() + System.lineSeparator()
        );
    }
}
