package nl.korthout.cantis;

import nl.korthout.cantis.cli.Arguments;
import nl.korthout.cantis.cli.CommandLineParser;

import java.io.File;
import java.time.Duration;
import java.time.Instant;

public class Cantis {

    public static void main(String[] args) {

        final Instant start = Instant.now();

        final Arguments arguments = new CommandLineParser(args).parseArguments();
        final String sourceDirectoryArgument = arguments.getSourceDirectory();
        final File rootDirectory = new File(sourceDirectoryArgument);

        System.out.println("Analysing source directory");

        final SourceDirectory sourceDirectory = new SourceDirectory(rootDirectory);

        System.out.println("Parsing " + sourceDirectory.getNumberOfFiles() + " java source files");

        new Glossary(sourceDirectory)
                .getDefinitions()
                .blockingSubscribe(System.out::println, System.err::println);

        System.out.println("Finished in: " + Duration.between(start, Instant.now()).toSeconds() + "s");
    }
}
