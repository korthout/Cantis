package nl.korthout.cantis;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

public class SourceDirectoryTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void rootDirectoryIsNotAllowedToBeNull() {
        assertThatNullPointerException().isThrownBy(() -> new SourceDirectory(null));
    }

    @Test
    public void nonexistingRootDirectoryResultsInException() {
        File nonexistingDirectory = new File("nonexistingDirectory");
        assertThatIllegalArgumentException().isThrownBy(() -> new SourceDirectory(nonexistingDirectory));
    }

    @Test
    public void nonDirectoryRootDirectoryResultsInException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new SourceDirectory(tempFolder.newFile()));
    }

    @Test
    public void pathToEmptyDirectoryResultsInEmptyListOfFiles() {
        var emptyDirectory = new SourceDirectory(tempFolder.getRoot());
        List<File> files = emptyDirectory.getFiles();
        assertThat(files).isEmpty();
    }

    @Test
    public void rootDirectoryWithoutJavaFilesResultsInEmptyList() throws IOException {
        tempFolder.newFile("Example.txt");
        var nonExmptyDirectory = new SourceDirectory(tempFolder.getRoot());
        List<File> javaFiles = nonExmptyDirectory.getFiles();
        assertThat(javaFiles).isEmpty();
    }

    @Test
    public void rootDirectoryWithJavaFilesResultsInListOfThatOneJavaFile() throws IOException {
        File javaFile = tempFolder.newFile("Example.java");
        var rootDirectory = new SourceDirectory(tempFolder.getRoot());
        List<File> javaFiles = rootDirectory.getFiles();
        assertThat(javaFiles).containsExactly(javaFile);
    }

    @Test
    public void rootDirectoryWithDeeperNestedJavaFileResultsInListOfThatJavaFile() throws IOException {
        File javaFile = tempFolder.newFile("Example.java");
        var nestedFolder = new TemporaryFolder(tempFolder.getRoot());
        nestedFolder.create();
        File nestedFile = nestedFolder.newFile("AnotherExample.java");
        var rootDirectory = new SourceDirectory(tempFolder.getRoot());
        List<File> javaFiles = rootDirectory.getFiles();
        assertThat(javaFiles).containsExactlyInAnyOrder(javaFile, nestedFile);
    }
}
