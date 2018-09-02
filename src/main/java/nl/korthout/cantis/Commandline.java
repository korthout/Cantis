package nl.korthout.cantis;

import io.airlift.airline.Cli;
import io.airlift.airline.Help;
import lombok.NonNull;

/**
 * Argument parser for the commandline interface.
 */
public interface Commandline {

    /**
     * Build a {@link Runnable} command from the provided arguments.
     * @param arguments The commandline arguments
     * @return The command
     */
    Runnable command(String[] arguments);

    /**
     * Defines what is supported by the commandline interface.
     */
    class ForCommands implements Commandline {

        private final Iterable<Class<? extends Runnable>> commands;

        /**
         * Constructor.
         * @param commands Commands supported by the cli
         */
        ForCommands(@NonNull Iterable<Class<? extends Runnable>> commands) {
            this.commands = commands;
        }

        @Override
        public Runnable command(String[] arguments) {
            var cli = Cli.<Runnable>builder("cantis")
                    .withDescription("the glossary generator")
                    .withCommands(commands)
                    .withCommand(Help.class)
                    .withDefaultCommand(Help.class)
                    .build();
            return cli.parse(arguments);
        }

    }

}
