package nl.korthout.cantis.cli;

import com.beust.jcommander.ParameterException;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CommandLineParserTest {

    @Test(expected = NullPointerException.class)
    public void nullArgumentsIsNotAllowed() {
        new CommandLineParser(null);
    }

    @Test
    public void defaultSourceDirectory() {
        // Arrange
        final String[] arguments = { };

        // Act
        final Arguments parsedArguments = new CommandLineParser(arguments).parseArguments();

        // Assert
        assertThat(parsedArguments, is(not(nullValue())));
        assertThat(parsedArguments.getSourceDirectory(), is("./src"));
    }

    @Test(expected = ParameterException.class)
    public void unknownArgumentsAreNotAllowed() {
        final String[] arguments = { "-unknownargument", "value" };
        new CommandLineParser(arguments).parseArguments();
    }

    @Test
    public void sourceDirectoryIsParsedFromArguments() {
        // Arrange
        final String[] arguments = { "-sourceDirectory", "./another-target" };

        // Act
        final Arguments parsedArguments = new CommandLineParser(arguments).parseArguments();

        // Assert
        assertThat(parsedArguments.getSourceDirectory(), is("./another-target"));
    }
}
