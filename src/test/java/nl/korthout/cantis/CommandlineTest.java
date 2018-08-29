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
        new Commandline.Configuration(null);
    }

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedAsProvidedArgs() {
        new Commandline.Configuration(new ListOf<>())
                .command(null);
    }

    @Test
    public void helpIsTheDefaultCommand() {
        Runnable command = new Commandline.Configuration(new ListOf<>())
                .command(new ArrayOf<String>().toArray());
        assertThat(command).isInstanceOf(Help.class);
    }

    @Test
    public void helpIsAlsoACommand() {
        Runnable command = new Commandline.Configuration(new ListOf<>())
                .command(new ArrayOf<>("help").toArray());
        assertThat(command).isInstanceOf(Help.class);
    }

    @Test(expected = ParseArgumentsUnexpectedException.class)
    public void unknownCommandsWontWork() {
        new Commandline.Configuration(new ListOf<>())
                .command(new ArrayOf<>("unknown").toArray());
    }

    @Test
    public void customCommandsCanBeSupported() {
        Runnable command = new Commandline.Configuration(new ListOf<>(CustomCommand.class))
                .command(new ArrayOf<>("custom").toArray());
        assertThat(command).isInstanceOf(CustomCommand.class);
    }

    private static class ArrayOf<X> {

        private final X[] arguments;

        @SafeVarargs
        ArrayOf(X... arguments) {
            this.arguments = arguments;
        }

        X[] toArray() {
            return arguments;
        }
    }

    @Command(name = "custom")
    public static class CustomCommand implements Runnable {

        @Override
        public void run() { }
    }
}