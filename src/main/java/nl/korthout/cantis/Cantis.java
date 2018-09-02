package nl.korthout.cantis;

import org.cactoos.list.ListOf;

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
            new ListOf<>(GenerateCommand.class)
        ).command(args).run();
    }

}
