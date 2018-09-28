package nl.korthout.cantis;

import io.airlift.airline.Cli;
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
     * Configures the argument parser for the commandline interface.
     */
    final class ForCommands implements Commandline {

        private final SupportedCommands commands;

        /**
         * Constructor.
         * @param commands Builds the commands supported by the cli
         */
        ForCommands(@NonNull SupportedCommands commands) {
            this.commands = commands;
        }

        @Override
        public Runnable command(String[] arguments) {
            var cli = Cli.<Runnable>builder("cantis")
                .withDescription("the glossary generator")
                .withCommands(commands.supported())
                .withDefaultCommand(commands.help())
                .build();
            return cli.parse(commands, arguments);
        }
    }
}
