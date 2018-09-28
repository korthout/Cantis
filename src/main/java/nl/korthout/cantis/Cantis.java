package nl.korthout.cantis;

import nl.korthout.cantis.SupportedCommands.SupportedCommandFactory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The glossary generator.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Cantis {

    /**
     * Main entry point of the program.
     * @param args The commandline arguments
     */
    public static void main(String[] args) {
        new Commandline.ForCommands(
            new SupportedCommandFactory()
        ).command(args).run();
    }
}
