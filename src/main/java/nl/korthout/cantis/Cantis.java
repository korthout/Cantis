package nl.korthout.cantis;

import io.airlift.airline.Cli;
import io.airlift.airline.Help;

public class Cantis {

    public static void main(String[] args) {
        Cli<Runnable> inputParser = Cli.<Runnable>builder("cantis")
                .withDescription("the glossary generator")
                .withCommands(Help.class, GenerateCommand.class)
                .withDefaultCommand(Help.class)
                .build();
        Runnable command = inputParser.parse(args);
        command.run();
    }

}
