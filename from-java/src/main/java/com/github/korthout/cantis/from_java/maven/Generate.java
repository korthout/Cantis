/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2018 Nico Korthout
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.korthout.cantis.from_java.maven;

import com.github.korthout.cantis.from_java.output.ToFile;
import com.github.korthout.cantis.from_java.codebase.Directory.FromSource;
import com.github.korthout.cantis.from_java.formatting.Format;
import com.github.korthout.cantis.from_java.glossary.GlossaryPrinter;
import com.github.korthout.cantis.from_java.output.ToLog;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Maven Plugin Mojo to generate a glossary for a Java project.
 * @since 0.2
 */
@Mojo(name = "generate")
@SuppressWarnings({"PMD.ImmutableField", "PMD.GuardLogStatement"})
public final class Generate extends AbstractMojo {

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

    /**
     * Output format. Defaults to {@code Format.PLAIN}.
     */
    @Parameter(name = "format", defaultValue = "plain")
    private String format;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        final var log = getLog();
        try {
            new GlossaryPrinter(
                new FromSource(this.source),
                new ToLog(log),
                // @checkstyle AvoidInlineConditionals (3 lines)
                this.target.isBlank()
                    ? new ToLog(log)
                    : new ToFile(this.target),
                Format.fromString(this.format)
            ).print();
        } catch (final IllegalArgumentException exception) {
            log.warn(String.format("Directory %s not found.", this.source));
            log.warn("You'll need to configure <source>");
            log.warn("for this plugin in your pom.xml.");
            log.warn("Note that this plugin is for");
            log.warn("single-module maven projects only.");
            log.warn("If you have a multi-module maven project,");
            log.warn("please take a look at our cli instead.");
        }
    }

}
