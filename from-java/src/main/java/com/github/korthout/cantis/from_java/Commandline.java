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
package com.github.korthout.cantis.from_java;

import com.github.korthout.cantis.from_java.commandline.SupportedCommands;
import io.airlift.airline.Cli;
import lombok.NonNull;

/**
 * Argument parser for the commandline interface.
 * @since 0.1
 */
public interface Commandline {

    /**
     * Build a {@link Runnable} command from the provided arguments.
     * @param arguments The commandline arguments
     * @return The command
     */
    Runnable command(String... arguments);

    /**
     * Configures the argument parser for the commandline interface.
     * @since 0.1
     */
    final class ForCommands implements Commandline {

        /**
         * Supported commands for this commandline.
         */
        private final SupportedCommands commands;

        /**
         * Main Constructor.
         * @param commands Builds the commands supported by the cli
         */
        ForCommands(final @NonNull SupportedCommands commands) {
            this.commands = commands;
        }

        @Override
        public Runnable command(final String... arguments) {
            final var cli = Cli.<Runnable>builder("cantis")
                .withDescription("the glossary generator")
                .withCommands(this.commands.supported())
                .withDefaultCommand(this.commands.help())
                .build();
            return cli.parse(this.commands, arguments);
        }
    }
}
