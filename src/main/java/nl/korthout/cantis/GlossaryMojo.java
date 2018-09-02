package nl.korthout.cantis;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Maven Plugin Mojo to execute the Glossary generator on a project.
 *
 * Based on the work in https://github.com/LivingDocumentation/livingdoc-maven-plugin
 */
@Mojo(name = "glossary")
public class GlossaryMojo extends AbstractMojo {

    /**
     * List of source directories to browse.
     */
    @Parameter(defaultValue = "${project.build.sourceDirectory}")
    private String source;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        var log = getLog();
        try {
            new CodebaseGlossary(
                new Codebase.CodebaseFromFiles(
                    new Directory.SourceDirectory(source)
                )
            ).definitions().forEach(definition -> log.info(definition.toString()));
        } catch (IllegalArgumentException e) {
            log.warn("Directory " + source + " not found." + System.lineSeparator()
                + "You'll need to configure <source> for this plugin in your pom.xml."
                + System.lineSeparator()
                + "Note that this plugin is for single-module maven projects only."
                + System.lineSeparator()
                + "If you have a multi-module maven project, please take a look at our cli instead."
            );
        }
    }

}
