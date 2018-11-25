package nl.korthout.cantis.fakes;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Does not print to an actual target,
 * but keeps track of the printed lines.
 */
public class FakePrintStream extends PrintStream {

    /**
     * Constructor.
     */
    public FakePrintStream() {
        super(new FakeOutputStream());
    }

    /**
     * Tells which lines have been printed so far.
     * @return All printed lines
     */
    public List<String> lines() {
        return (
            (FakeOutputStream) super.out
        ).lines();
    }

    /**
     * Does not write to an actual target,
     * but keeps track of the written lines.
     */
    private static class FakeOutputStream extends OutputStream {

        private List<String> written;
        private String line;

        /**
         * Constructor.
         */
        private FakeOutputStream() {
            this.written = new ArrayList<>();
            this.line = "";
        }

        @Override
        public void write(int byteToWrite) {
            var charToWrite = String.valueOf((char) byteToWrite);
            line = line.concat(charToWrite);
            if (charToWrite.equals(System.lineSeparator())) {
                written.add(line);
                line = "";
            }
        }

        /**
         * Tells which lines have been written so far.
         * @return All written lines
         */
        List<String> lines() {
            return written;
        }
    }
}
