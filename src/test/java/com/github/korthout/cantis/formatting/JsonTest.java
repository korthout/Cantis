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
package com.github.korthout.cantis.formatting;

import com.github.korthout.cantis.fakes.NoDefinitions;
import com.github.korthout.cantis.fakes.WithDefinitions;
import com.github.korthout.cantis.glossary.Definition;
import org.assertj.core.api.Assertions;
import org.cactoos.text.TextOf;
import org.junit.Test;

/**
 * Unit tests for {@code Json} objects.
 * @since 0.2
 * @checkstyle StringLiteralsConcatenation (51 lines)
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class JsonTest {

    /**
     * System specific line seperator (e.g. `\r\n` or `\n`).
     */
    private static final String ENDLINE = System.lineSeparator();

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNull() {
        new Json(null);
    }

    @Test
    public void formattedCanRepresentAGlossaryWithoutDefinitions() throws Exception {
        Assertions.assertThat(
            new Json(new NoDefinitions()).formatted().asString()
        ).isEqualTo(
            new TextOf(
                "{" + JsonTest.ENDLINE
                    + "    \"definitions\": []" + JsonTest.ENDLINE
                    + "}"
            ).asString()
        );
    }

    @Test
    public void formattedAlsoRepresentsEachIndividualDefinition() throws Exception {
        Assertions.assertThat(
            new Json(
                new WithDefinitions(
                    new Definition("Something", "Not everything"),
                    new Definition("Everything", "All somethings")
                )
            ).formatted().asString()
        ).isEqualTo(
            new TextOf(
                "{" + JsonTest.ENDLINE
                    + "    \"definitions\": [{" + JsonTest.ENDLINE
                    + "        \"term\": \"Something\"," + JsonTest.ENDLINE
                    + "        \"description\": \"Not everything\"" + JsonTest.ENDLINE
                    + "    }, {" + JsonTest.ENDLINE
                    + "        \"term\": \"Everything\"," + JsonTest.ENDLINE
                    + "        \"description\": \"All somethings\"" + JsonTest.ENDLINE
                    + "    }]" + JsonTest.ENDLINE
                    + "}"
            ).asString()
        );
    }

}
