package nl.korthout.cantis;

import nl.korthout.cantis.Codebase.CodebaseFromFiles;
import nl.korthout.cantis.Directory.SourceDirectory;

import org.cactoos.Text;

import java.time.Duration;

import io.airlift.airline.Arguments;
import io.airlift.airline.Command;

/**
 * Generate a glossary from your sourcecode.
 *
 * <p><b>Note</b>: This class is more procedural than other classes,
 * because it sits at the boundary between the user and the application's internals.</p>
 */
@Command(name = "generate", description = "Generate a glossary from your sourcecode")
public final class GenerateCommand implements Runnable {

    @Arguments(title = "source", description = "The root directory of the source code")
    private String source;

    /**
     * Constructor.
     */
    public GenerateCommand() {
        this.source = "."; // default
    }

    @Override
    public void run() {
        Duration runtime = new TimedRunnable(() -> {
            var directory = new SourceDirectory(source);
            print(String.format(
                "Scanning %d java source files for @GlossaryTerm annotation",
                directory.files().size()
            ));
            print(new FormattedGlossary(
                new CodebaseGlossary(
                    new CodebaseFromFiles(directory)
                )
            ).formatted());
        }).runtime();
        print("Finished in: " + runtime.toSeconds() + "s");
    }

    private void print(Text text) {
        print(text.toString());
    }

    private void print(String text) {
        System.out.println(text);
    }

}
