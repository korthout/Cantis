package nl.korthout.cantis;

import org.cactoos.list.ListOf;

/**
 * The glossary generator.
 */
public class Cantis {

    public static void main(String[] args) {
        new Commandline.Configuration(
                new ListOf<>(GenerateCommand.class)
        ).command(args).run();
    }

}
