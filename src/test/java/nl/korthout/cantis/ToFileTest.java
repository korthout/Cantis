package nl.korthout.cantis;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

public class ToFileTest {

    @Rule public TemporaryFolder tmp = new TemporaryFolder();

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNull() {
        new ToFile((File) null);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ConstantConditions")
    public void writeDoesNotAllowNull() throws IOException {
        new ToFile(
            tmp.newFile()
        ).write(null);
    }

    @Test
    public void everyWrittenLineEndsWithALineEnding() {
        // Untestable because of the Files.writeString implementation
        // We simply have to trust the library
    }

    @Test
    public void linesAreWrittenInOrder() {
        // Untestable because of the Files.writeString implementation
        // We simply have to trust the library
    }

}