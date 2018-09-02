package nl.korthout.cantis;

import nl.korthout.cantis.Directory.SourceDirectory;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class SourceDirectoryTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test(expected = NullPointerException.class)
    public void constructorDoesNotAllowNull() {
        new SourceDirectory((File) null);
    }

    @Test
    public void nonexistingDirectoryResultsInException() {
        assertThatIllegalArgumentException().isThrownBy(
            () -> new SourceDirectory(
                new File("nonexisting")
            )
        );
    }

    @Test
    public void nonDirectoryResultsInException() {
        assertThatIllegalArgumentException().isThrownBy(
            () -> new SourceDirectory(
                tempFolder.newFile()
            )
        );
    }

    @Test
    public void emptyDirectoryResultsInEmptyCollectionOfFiles() {
        assertThat(
            new SourceDirectory(
                tempFolder.getRoot()
            ).files()
        ).isEmpty();
    }

    @Test
    public void pathToEmptyDirectoryResultsInEmptyCollection() {
        assertThat(
            new SourceDirectory(
                tempFolder.getRoot().getPath()
            ).files()
        ).isEmpty();
    }

    @Test
    public void directoryWithoutJavaFilesResultsInEmptyCollection() throws IOException {
        tempFolder.newFile("Example.txt");
        assertThat(
            new SourceDirectory(
                tempFolder.getRoot()
            ).files()
        ).isEmpty();
    }

    @Test
    public void directoryWithJavaFilesResultsInCollectionOfThatOneJavaFile() throws IOException {
        File javaFile = tempFolder.newFile("Example.java");
        assertThat(
            new SourceDirectory(
                tempFolder.getRoot()
            ).files()
        ).containsExactly(javaFile);
    }

    @Test
    public void directoryWithDeeperNestedJavaFileResultsInCollectionOfThatJavaFile() throws IOException {
        File javaFile = tempFolder.newFile("Example.java");
        var nestedFolder = new TemporaryFolder(tempFolder.getRoot());
        nestedFolder.create();
        File nestedFile = nestedFolder.newFile("AnotherExample.java");
        assertThat(
            new SourceDirectory(
                tempFolder.getRoot()
            ).files()
        ).containsExactlyInAnyOrder(javaFile, nestedFile);
    }

}
