package nl.korthout.cantis;

import org.cactoos.list.ListOf;
import org.junit.Test;

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
            new ListOf<>()
        ).command(null);
    }

    @Test
    public void helpIsTheDefaultCommand() {
        assertThat(
            new Commandline.ForCommands(
                new ListOf<>()
            ).command(new String[]{})
        ).isInstanceOf(Help.class);
    }

    @Test
    public void helpIsAlsoACommand() {
        assertThat(
            new Commandline.ForCommands(
                new ListOf<>()
            ).command(new String[]{"help"})
        ).isInstanceOf(Help.class);
    }

    @Test(expected = ParseArgumentsUnexpectedException.class)
    public void unknownCommandsWontWork() {
        new Commandline.ForCommands(
            new ListOf<>()
        ).command(new String[]{"unknown"});
    }

    @Test
    public void customCommandsCanBeSupported() {
        assertThat(
            new Commandline.ForCommands(
                new ListOf<>(CustomCommand.class)
            ).command(new String[]{"custom"})
        ).isInstanceOf(CustomCommand.class);
    }

    @Command(name = "custom")
    public static class CustomCommand implements Runnable {

        @Override
        public void run() {
        }
    }
}