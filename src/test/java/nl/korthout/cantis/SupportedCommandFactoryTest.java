package nl.korthout.cantis;

import nl.korthout.cantis.SupportedCommands.SupportedCommandFactory;

import org.junit.Test;

import io.airlift.airline.Help;

import static org.assertj.core.api.Assertions.assertThat;

public class SupportedCommandFactoryTest {

    @Test
    public void supportsHelp() {
        assertThat(
            new SupportedCommandFactory().help()
        ).isEqualTo(Help.class);
    }

    @Test
    public void supportsHelpAsCommand() {
        assertThat(
            new SupportedCommandFactory().supported()
        ).contains(Help.class);
    }

    @Test
    public void canBuildTheActualHelpCommand() {
        assertThat(
            new SupportedCommandFactory().createInstance(Help.class)
        ).isInstanceOf(Help.class);
    }

    @Test
    public void canBuildTheGenerateCommand() {
        assertThat(
            new SupportedCommandFactory().createInstance(GenerateCommand.class)
        ).isInstanceOf(GenerateCommand.class);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ConstantConditions")
    public void createInstanceDoesNotAllowNull() {
        new SupportedCommandFactory().createInstance(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateInstanceIfCommandClassIsNotSupported() {
        new SupportedCommandFactory().createInstance(Runnable.class);
    }
}