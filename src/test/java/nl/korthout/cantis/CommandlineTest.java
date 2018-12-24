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
package nl.korthout.cantis;

import io.airlift.airline.Command;
import io.airlift.airline.Help;
import io.airlift.airline.ParseArgumentsUnexpectedException;
import java.util.Collection;
import org.assertj.core.api.Assertions;
import org.cactoos.list.ListOf;
import org.junit.Test;

/**
 * Unit tests for {@code Commandline} objects.
 * @since 0.1
 */
@SuppressWarnings("PMD.ProhibitPlainJunitAssertionsRule")
public class CommandlineTest {

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedInConstructor() {
        new Commandline.ForCommands(null);
    }

    @Test(expected = NullPointerException.class)
    public void nullIsNotAllowedAsProvidedArgs() {
        new Commandline.ForCommands(
            new CustomCommmands()
        ).command(null);
    }

    @Test
    public void helpIsTheDefaultCommand() {
        Assertions.assertThat(
            new Commandline.ForCommands(
                new CustomCommmands()
            ).command()
        ).isInstanceOf(Help.class);
    }

    @Test
    public void helpIsAlsoACommand() {
        Assertions.assertThat(
            new Commandline.ForCommands(
                new CustomCommmands()
            ).command("help")
        ).isInstanceOf(Help.class);
    }

    @Test(expected = ParseArgumentsUnexpectedException.class)
    public void unknownCommandsWontWork() {
        new Commandline.ForCommands(
            new CustomCommmands()
        ).command("unknown");
    }

    @Test
    public void customCommandsCanBeSupported() {
        Assertions.assertThat(
            new Commandline.ForCommands(
                new CustomCommmands()
            ).command("custom")
        ).isInstanceOf(CustomCommand.class);
    }

    /**
     * Supports fake command objects.
     */
    private static final class CustomCommmands implements SupportedCommands {

        @Override
        public Collection<Class<? extends Runnable>> supported() {
            return new ListOf<>(CustomCommand.class, Help.class);
        }

        @Override
        public Class<? extends Runnable> help() {
            return Help.class;
        }

        // @checkstyle ReturnCount (2 lines)
        @Override
        @SuppressWarnings("PMD.OnlyOneReturn")
        public Runnable createInstance(final Class<?> type) {
            if (type == CustomCommand.class) {
                return new CustomCommand();
            }
            return new Help();
        }
    }

    /**
     * Fake object that acts like a {@code Command},
     * but does nothing.
     */
    @Command(name = "custom")
    private static final class CustomCommand implements Runnable {

        @Override
        public void run() {
            // This fake command does not have to do anything
        }
    }
}
