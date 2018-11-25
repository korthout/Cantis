package nl.korthout.cantis;

import nl.korthout.cantis.Directory.FromSource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class FromSourceTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test(expected = NullPointerException.class)
    @SuppressWarnings("ConstantConditions")
    public void constructorDoesNotAllowNull() {
        new FromSource((File) null);
    }

    @Test
    public void nonexistingDirectoryResultsInException() {
        assertThatIllegalArgumentException().isThrownBy(
            () -> new FromSource(
                new File("nonexisting")
            )
        );
    }

    @Test
    public void nonDirectoryResultsInException() {
        assertThatIllegalArgumentException().isThrownBy(
            () -> new FromSource(
                tempFolder.newFile()
            )
        );
    }

    @Test
    public void emptyDirectoryResultsInEmptyCollectionOfFiles() {
        assertThat(
            new FromSource(
                tempFolder.getRoot()
            ).files()
        ).isEmpty();
    }

    @Test
    public void pathToEmptyDirectoryResultsInEmptyCollection() {
        assertThat(
            new FromSource(
                tempFolder.getRoot().getPath()
            ).files()
        ).isEmpty();
    }

    @Test
    public void directoryWithoutJavaFilesResultsInEmptyCollection() throws IOException {
        tempFolder.newFile("Example.txt");
        assertThat(
            new FromSource(
                tempFolder.getRoot()
            ).files()
        ).isEmpty();
    }

    @Test
    public void directoryWithJavaFilesResultsInCollectionOfThatOneJavaFile() throws IOException {
        File javaFile = tempFolder.newFile("Example.java");
        assertThat(
            new FromSource(
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
            new FromSource(
                tempFolder.getRoot()
            ).files()
        ).containsExactlyInAnyOrder(javaFile, nestedFile);
    }

}
