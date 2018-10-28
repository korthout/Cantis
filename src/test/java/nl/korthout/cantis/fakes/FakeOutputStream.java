package nl.korthout.cantis.fakes;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FakeOutputStream extends OutputStream {

    private List<String> written;
    private String line;

    public FakeOutputStream() {
        this.written = new ArrayList<>();
        this.line = "";
    }

    @Override
    public void write(int byteToWrite) {
        final String charToWrite = String.valueOf((char) byteToWrite);
        line = line.concat(charToWrite);
        if (charToWrite.equals(System.lineSeparator())) {
            written.add(line);
            line = "";
        }
    }

    public List<String> lines() {
        return written;
    }
}
