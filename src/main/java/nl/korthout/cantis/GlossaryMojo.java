package nl.korthout.cantis;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Maven Plugin Mojo to execute the Glossary generator on a project.
 *
 * Based on the work in https://github.com/LivingDocumentation/livingdoc-maven-plugin
 */
@Mojo(name = "glossary")
public class GlossaryMojo extends AbstractMojo {

    /**
     * List of source directories to browse
     */
    @Parameter(defaultValue = "${project.build.sourceDirectory}")
    private List<String> sources;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        var log = getLog();
        new Glossary(getSourceFiles())
                .getDefinitions()
                .forEach(definition -> log.info(definition.toString()));
    }

    private List<File> getSourceFiles() {
        return sources.stream()
                    .flatMap(this::findFilesInDirectory)
                    .map(Path::toFile)
                    .collect(toList());
    }

    private Stream<Path> findFilesInDirectory(String source) {
        try {
            return Files.find(Paths.get(source), 999, (p, bfa) -> bfa.isRegularFile());
        } catch (IOException e) {
            // TODO: Do something better than return null.
            return null;
        }
    }
}
