package nl.korthout.cantis;

import org.cactoos.text.FormattedText;

import java.time.Duration;

import lombok.NonNull;

/**
 * Generate a glossary from your sourcecode.
 *
 * <p><b>Note</b>: This class is more procedural than other classes,
 * because it sits at the boundary between the user and the application's internals.</p>
 */
public final class PrintableGlossary implements Printable {

    private final Directory directory;
    private final Destination info;
    private final Destination target;

    /**
     * Constructor.
     * @param directory Root directory of the source code
     * @param info Information will be outputted to this destination
     * @param target Formatted glossary will be outputted to this destination
     */
    PrintableGlossary(
        @NonNull Directory directory,
        @NonNull Destination info,
        @NonNull Destination target
    ) {
        this.directory = directory;
        this.info = info;
        this.target = target;
    }

    /**
     * Shorthand constructor to direct all output to the same destination.
     * @param directory Root directory of the source code
     * @param output The formatted glossary and information will be outputted to this destination
     */
    PrintableGlossary(Directory directory, Destination output) {
        this(directory, output, output);
    }

    @Override
    public void print() {
        Duration runtime = new TimeableRunnable(() -> {
            info.write(
                new FormattedText(
                    "Scanning %d java source files for @GlossaryTerm annotation",
                    directory.files().size()
                ));
            target.write(
                new FormattedGlossary(
                    new CodebaseGlossary(
                        new Codebase.CodebaseFromFiles(directory)
                    )
                ).formatted()
            );
        }).runtime();
        info.write(new FormattedText("Finished in: %ss", runtime.toSeconds()));
    }

}
