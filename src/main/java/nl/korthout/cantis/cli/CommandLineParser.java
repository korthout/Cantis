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
        Arguments arguments = new Arguments();
        JCommander.newBuilder()
                .addObject(arguments)
                .build()
                .parse(this.arguments);
        return arguments;
    }
}
