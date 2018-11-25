package nl.korthout.cantis;

import nl.korthout.cantis.Directory.FromSource;

import io.airlift.airline.Arguments;
import io.airlift.airline.Command;
import io.airlift.airline.Option;

/**
 * Commandline command to generate a glossary from sourcecode.
 */
@Command(name = "generate", description = "Generate a glossary from your sourcecode")
public final class GenerateCommand implements Runnable {

    @Arguments(title = "source", description = "The root directory of the source code")
    private String source;

    @Option(title = "target", name = {"--target", "-t"}, description = "Path to output file")
    private String target;

    /**
     * Constructor.
     */
    GenerateCommand() {
        this.source = "."; // default
        this.target = ""; // default
    }

    @Override
    public void run() {
        new PrintableGlossary(
            new FromSource(source),
            new ToPrintStream(System.out),
            target.isBlank()
                ? new ToPrintStream(System.out)
                : new ToFile(target)
        ).print();
    }
}
