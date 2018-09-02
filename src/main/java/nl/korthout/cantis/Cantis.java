package nl.korthout.cantis;

import nl.korthout.cantis.command.GenerateCommand;

import org.cactoos.list.ListOf;

/**
 * The glossary generator.
 */
public class Cantis {

    public static void main(String[] args) {
        new Commandline.ForCommands(
                new ListOf<>(GenerateCommand.class)
        ).command(args).run();
    }

}
