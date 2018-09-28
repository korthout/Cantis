package nl.korthout.cantis;

import org.cactoos.Text;
import org.cactoos.text.FormattedText;

import java.io.PrintStream;
import java.time.Duration;

import lombok.NonNull;
import lombok.SneakyThrows;

/**
 * Generate a glossary from your sourcecode.
 *
 * <p><b>Note</b>: This class is more procedural than other classes,
 * because it sits at the boundary between the user and the application's internals.</p>
 */
public final class PrintableGlossary implements Printable {

    private final Directory directory;
    private final PrintStream out;

    /**
     * Constructor.
     * @param directory Root directory of the source code
     * @param out Output will be printed to this stream
     */
    PrintableGlossary(@NonNull Directory directory, @NonNull PrintStream out) {
        this.directory = directory;
        this.out = out;
    }

    @Override
    public void print() {
        Duration runtime = new TimedRunnable(() -> {
            println(new FormattedText(
                "Scanning %d java source files for @GlossaryTerm annotation",
                directory.files().size()
            ));
            println(new FormattedGlossary(
                new CodebaseGlossary(
                    new Codebase.CodebaseFromFiles(directory)
                )
            ).formatted());
        }).runtime();
        println(new FormattedText("Finished in: %ss", runtime.toSeconds()));
    }

    @SneakyThrows
    private void println(Text text) {
        out.println(text.asString());
    }

}
