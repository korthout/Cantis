package nl.korthout.cantis;

import io.airlift.airline.Arguments;
import io.airlift.airline.Command;
import lombok.Data;

@Command(name = "generate", description = "Generate a glossary from your sourcecode")
@Data
public class GenerateCommand extends TimedCommand {

    @Arguments(title = "source-directory", description = "The root directory of the source code")
    private String source = ".";

    @Override
    void execute() {
        System.out.println("Analysing source directory " + source);
        var sourceDirectory = new SourceDirectory(source);
        System.out.println("Parsing " + sourceDirectory.getNumberOfFiles() + " java source files");
        new Glossary(sourceDirectory)
                .getDefinitions()
                .forEach(System.out::println);
    }
}
