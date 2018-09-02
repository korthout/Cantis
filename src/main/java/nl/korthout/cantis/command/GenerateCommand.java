package nl.korthout.cantis.command;

import nl.korthout.cantis.Definition;
import nl.korthout.cantis.Glossary;
import nl.korthout.cantis.Sources;

import org.cactoos.io.InputOf;
import org.cactoos.io.LengthOf;
import org.cactoos.io.OutputTo;
import org.cactoos.io.StdoutOutput;
import org.cactoos.io.TeeInput;
import org.cactoos.text.TextOf;

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
public class GenerateCommand implements Runnable {

    @Arguments(title = "source", description = "The root directory of the source code")
    private String source;

    public GenerateCommand() {
        this.source = "."; // default
    }

    @Override
    public void run() {
        Duration runtime = new TimedRunnable(this::generate).runtime();
        print("Finished in: " + runtime.toSeconds() + "s");
    }

    // TODO: Has signs of procedural code that needs to be rewritten
    // TODO: Use OO output instead of utility methods for the output
    private void generate() {
        print("Using source directory " + source);
        var sources = new Sources(source);
        print("Parsing " + sources.getNumberOfFiles() + " java source files");
        new Glossary(sources)
                .getDefinitions()
                .map(Definition::toString)
                .forEach(this::print);
    }

    private void print(String text) {
        new LengthOf(
                new TeeInput(
                        new InputOf(new TextOf(text + "\n")),
                        new OutputTo(
                                new StdoutOutput().stream()
                        )
                )
        ).intValue();
    }

}
