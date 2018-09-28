package nl.korthout.cantis;

import nl.korthout.cantis.Directory.SourceDirectory;

import io.airlift.airline.Arguments;
import io.airlift.airline.Command;

/**
 * Commandline command to generate a glossary from sourcecode.
 */
@Command(name = "generate", description = "Generate a glossary from your sourcecode")
public final class GenerateCommand implements Runnable {

    @Arguments(title = "source", description = "The root directory of the source code")
    private String source;

    /**
     * Constructor.
     */
    GenerateCommand() {
        this.source = "."; // default
    }

    @Override
    public void run() {
        new PrintableGlossary(
            new SourceDirectory(source),
            System.out
        ).print();
    }
}
