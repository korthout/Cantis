package nl.korthout.cantis;

import org.cactoos.list.ListOf;
import org.junit.Test;

import java.util.Collection;

import io.airlift.airline.Command;
import io.airlift.airline.Help;
import io.airlift.airline.ParseArgumentsUnexpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandlineTest {

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedInConstructor() {
        new Commandline.ForCommands(null);
    }

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedAsProvidedArgs() {
        new Commandline.ForCommands(
            new CustomCommmands()
        ).command(null);
    }

    @Test
    public void helpIsTheDefaultCommand() {
        assertThat(
            new Commandline.ForCommands(
                new CustomCommmands()
            ).command(new String[]{})
        ).isInstanceOf(Help.class);
    }

    @Test
    public void helpIsAlsoACommand() {
        assertThat(
            new Commandline.ForCommands(
                new CustomCommmands()
            ).command(new String[]{"help"})
        ).isInstanceOf(Help.class);
    }

    @Test(expected = ParseArgumentsUnexpectedException.class)
    public void unknownCommandsWontWork() {
        new Commandline.ForCommands(
            new CustomCommmands()
        ).command(new String[]{"unknown"});
    }

    @Test
    public void customCommandsCanBeSupported() {
        assertThat(
            new Commandline.ForCommands(
                new CustomCommmands()
            ).command(new String[]{"custom"})
        ).isInstanceOf(CustomCommand.class);
    }

    private static final class CustomCommmands implements SupportedCommands {

        @Override
        public Collection<Class<? extends Runnable>> supported() {
            return new ListOf<>(CustomCommand.class, Help.class);
        }

        @Override
        public Class<? extends Runnable> help() {
            return Help.class;
        }

        @Override
        public Runnable createInstance(Class<?> type) {
            if (type == CustomCommand.class) {
                return new CustomCommand();
            }
            return new Help();
        }
    }

    @Command(name = "custom")
    private static final class CustomCommand implements Runnable {

        @Override
        public void run() {
        }
    }
}