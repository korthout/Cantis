package nl.korthout.cantis;

import io.airlift.airline.Cli;
import io.airlift.airline.Help;
import lombok.NonNull;

/**
 * Argument parser for the commandline interface.
 */
public interface Commandline {

    /**
     * Build a {@link Runnable} command from the provided args.
     * @param args The commandline arguments
     * @return The command
     */
    Runnable command(String[] args);

    /**
     * Defines what is supported by the commandline interface.
     */
    class Configuration implements Commandline {

        private Iterable<Class<? extends Runnable>> supportedCommands;

        /**
         * Constructor.
         * @param supportedCommands Commands supported by the cli
         */
        Configuration(@NonNull Iterable<Class<? extends Runnable>> supportedCommands) {
            this.supportedCommands = supportedCommands;
        }

        @Override
        public Runnable command(String[] args) {
            return constructCli().parse(args);
        }

        private Cli<Runnable> constructCli() {
            return Cli.<Runnable>builder("cantis")
                    .withDescription("the glossary generator")
                    .withCommands(supportedCommands)
                    .withCommand(Help.class)
                    .withDefaultCommand(Help.class)
                    .build();
        }

    }

}
