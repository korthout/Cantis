package nl.korthout.cantis.cli;

import com.beust.jcommander.Parameter;

import lombok.Getter;

@Getter
public class Arguments {
    private static final String DEFAULT_SOURCE_DIRECTORY = "./src";

    @Parameter(names = "-sourceDirectory")
    private String sourceDirectory;

    public Arguments() {
        this.sourceDirectory = DEFAULT_SOURCE_DIRECTORY;
    }
}
