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
package com.github.korthout.cantis;

import io.airlift.airline.CommandFactory;
import io.airlift.airline.Help;
import java.util.Collection;
import lombok.NonNull;
import org.cactoos.list.ListOf;

/**
 * Factory for {@link Runnable} commands
 * that can also tell which commands it supports.
 * @since 0.1
 */
public interface SupportedCommands extends CommandFactory<Runnable> {

    /**
     * Builds a collection of the supported {@link Runnable} commands.
     * @return The specific classes of the supported commands
     */
    Collection<Class<? extends Runnable>> supported();

    /**
     * Builds the help command.
     * @return The help command class
     */
    Class<? extends Runnable> help();

    /**
     * Configuration of supported commands and their construction.
     * @since 0.1
     */
    final class SupportedCommandFactory implements SupportedCommands {

        // @checkstyle ReturnCount (3 lines)
        @Override
        public Runnable createInstance(final @NonNull Class<?> type) {
            if (type.equals(Help.class)) {
                return new Help();
            } else if (type.equals(GenerateCommand.class)) {
                return new GenerateCommand();
            }
            throw new IllegalArgumentException("Unknown command type");
        }

        @Override
        public Collection<Class<? extends Runnable>> supported() {
            return new ListOf<>(
                Help.class,
                GenerateCommand.class
            );
        }

        @Override
        public Class<? extends Runnable> help() {
            return Help.class;
        }
    }
}
