package nl.korthout.cantis;

import org.junit.Test;

import static org.junit.Assert.*;

public class GenerateCommandTest {

    @Test
    public void test() {
        GenerateCommand command = new GenerateCommand();
        command.run();
    }

}