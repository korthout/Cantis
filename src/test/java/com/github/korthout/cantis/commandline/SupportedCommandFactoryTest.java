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
package com.github.korthout.cantis.commandline;

import com.github.korthout.cantis.commandline.SupportedCommands.SupportedCommandFactory;
import io.airlift.airline.Help;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * Unit tests for {@code SupportedCommandFactory} objects.
 * @since 0.1.1
 */
@SuppressWarnings("PMD.ProhibitPlainJunitAssertionsRule")
public class SupportedCommandFactoryTest {

    @Test
    public void supportsHelp() {
        Assertions.assertThat(
            new SupportedCommandFactory().help()
        ).isEqualTo(Help.class);
    }

    @Test
    public void supportsHelpAsCommand() {
        Assertions.assertThat(
            new SupportedCommandFactory().supported()
        ).contains(Help.class);
    }

    @Test
    public void canBuildTheActualHelpCommand() {
        Assertions.assertThat(
            new SupportedCommandFactory().createInstance(Help.class)
        ).isInstanceOf(Help.class);
    }

    @Test
    public void canBuildTheGenerateCommand() {
        Assertions.assertThat(
            new SupportedCommandFactory().createInstance(Generate.class)
        ).isInstanceOf(Generate.class);
    }

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ConstantConditions")
    public void createInstanceDoesNotAllowNull() {
        new SupportedCommandFactory().createInstance(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateInstanceIfCommandClassIsNotSupported() {
        new SupportedCommandFactory().createInstance(Runnable.class);
    }
}
