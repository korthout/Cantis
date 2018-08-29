package nl.korthout.cantis;

import java.time.Duration;

import io.airlift.airline.Arguments;
import io.airlift.airline.Command;

@Command(name = "generate", description = "Generate a glossary from your sourcecode")
public class GenerateCommand implements Runnable {

    @Arguments(title = "source", description = "The root directory of the source code")
    private String source;

    public GenerateCommand() {
        this.source = "."; // default
    }

    @Override
    public void run() {
        Duration runtime = new TimedRunnable(this::generate).runtime();
        System.out.println("Finished in: " + runtime.toSeconds() + "s");
    }

    private void generate() {
        System.out.println("Using source directory " + source);
        var sources = new Sources(source);
        System.out.println("Parsing " + sources.getNumberOfFiles() + " java source files");
        new Glossary(sources)
                .getDefinitions()
                .forEach(System.out::println);
    }

}
