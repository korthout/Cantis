package nl.korthout.cantis.cli;

import com.beust.jcommander.ParameterException;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CommandLineParserTest {

    @Test(expected = NullPointerException.class)
    public void nullArgumentsIsNotAllowed() {
        String[] arguments = null;
        new CommandLineParser(arguments);
    }

    @Test
    public void defaultSourceDirectory() {
        String[] arguments = { };
        var parsedArguments = new CommandLineParser(arguments).parseArguments();
        assertThat(parsedArguments.getSourceDirectory()).isEqualTo("./src");
    }

    @Test(expected = ParameterException.class)
    public void unknownArgumentsAreNotAllowed() {
        String[] arguments = { "-unknownargument", "value" };
        new CommandLineParser(arguments).parseArguments();
    }

    @Test
    public void sourceDirectoryIsParsedFromArguments() {
        String[] arguments = { "-sourceDirectory", "./another-target" };
        var parsedArguments = new CommandLineParser(arguments).parseArguments();
        assertThat(parsedArguments.getSourceDirectory()).isEqualTo("./another-target");
    }
}
