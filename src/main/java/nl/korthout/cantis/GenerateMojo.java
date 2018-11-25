package nl.korthout.cantis;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Maven Plugin Mojo to generate a glossary for a Java project.
 * @goal Generate a glossary from sourcecode
 * @phase generate-resources
 */
@Mojo(name = "generate")
public class GenerateMojo extends AbstractMojo {

    /**
     * The root directory of the source code.
     */
    @Parameter(defaultValue = "${project.build.sourceDirectory}")
    private String source;

    /**
     * Path to output file.
     */
    @Parameter(name = "target")
    private String target = "";

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        var log = getLog();
        try {
            new PrintableGlossary(
                new Directory.FromSource(source),
                new ToLog(log),
                target.isBlank()
                    ? new ToLog(log)
                    : new ToFile(target)
            ).print();
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
