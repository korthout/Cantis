package nl.korthout.cantis.cli;

import com.beust.jcommander.JCommander;

import lombok.NonNull;
import lombok.Value;

@Value
public class CommandLineParser {

    @NonNull private final String[] arguments;

    /**
     * Parse the arguments provided to this CommandLineParser.
     * @return an instance of Arguments from the provided arguments.
     */
    public Arguments parseArguments() {
        var parsedArguments = new Arguments();
        JCommander.newBuilder()
                .addObject(parsedArguments)
                .build()
                .parse(this.arguments);
        return parsedArguments;
    }
}
