package nl.korthout.cantis;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

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
        final List<File> sourceFiles = Observable.fromIterable(this.sources)
                .flatMap(source -> Observable.fromIterable(
                        Files.find(Paths.get(source), 999, (p, bfa) -> bfa.isRegularFile()).collect(toList())))
                .map(Path::toFile)
                .collectInto(new ArrayList<File>(), ArrayList::add)
                .blockingGet();

        final Log log = getLog();

        final Glossary glossary = new Glossary(sourceFiles);
        final Observable<Definition> definitions = glossary.getDefinitions();
        definitions.blockingSubscribe((definition -> log.info(definition.toString())),
                log::error,
                () -> log.info("Glossary generated"));
    }
}
