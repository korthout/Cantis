package nl.korthout.cantis;

import org.cactoos.list.ListOf;

import java.util.Collection;

import io.airlift.airline.CommandFactory;
import io.airlift.airline.Help;
import lombok.NonNull;

/**
 * Factory for {@link Runnable} commands
 * that can also tell which commands it supports.
 */
public interface SupportedCommands extends CommandFactory<Runnable> {

    /**
     * Builds a collection of the supported {@link Runnable} commands.
     * @return The specific classes of the supported commands
     */
    Collection<Class<? extends Runnable>> supported();

    /**
     * Builds the help command.
     * @return The help command class
     */
    Class<? extends Runnable> help();

    /**
     * Configuration of supported commands and their construction.
     */
    final class SupportedCommandFactory implements SupportedCommands {

        private static final Class<Help> HELP_CLASS = Help.class;

        @Override
        public Runnable createInstance(@NonNull Class<?> type) {
            if (type.equals(HELP_CLASS)) {
                return new Help();
            } else if (type.equals(GenerateCommand.class)) {
                return new GenerateCommand();
            }
            throw new IllegalArgumentException("Unknown command type");
        }

        @Override
        public Collection<Class<? extends Runnable>> supported() {
            return new ListOf<>(
                HELP_CLASS,
                GenerateCommand.class
            );
        }

        @Override
        public Class<? extends Runnable> help() {
            return HELP_CLASS;
        }
    }
}
