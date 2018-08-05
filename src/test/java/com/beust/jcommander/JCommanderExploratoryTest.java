package com.beust.jcommander;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Exploratory tests for the JCommander commandline-interface library.
 * To try-out and document the usage of this library.
 */
public class JCommanderExploratoryTest {

    @Test
    public void testTheProvidedExampleCode() {
        // Arrange
        final String[] providedArgValues = { "-log", "2", "-groups", "unit" };
        Args args = new Args();

        // Act
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(providedArgValues);

        // Assert
        assertThat(args.verbose).isEqualTo(2);
        assertThat(args.groups).isEqualTo("unit");
        assertThat(args.debug).isFalse();
    }

    /**
     * From the standard example
     */
    private class Args {
        @Parameter(names = { "-log", "-verbose" }, description = "Level of verbosity")
        private Integer verbose = 1;

        @Parameter(names = "-groups", description = "Comma-separated list of group names to be run")
        private String groups;

        @Parameter(names = "-debug", description = "Debug mode")
        private boolean debug = false;
    }
}
