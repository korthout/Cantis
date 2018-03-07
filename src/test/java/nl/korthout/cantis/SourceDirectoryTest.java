package nl.korthout.cantis;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import lombok.val;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class SourceDirectoryTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test(expected = NullPointerException.class)
    public void rootDirectoryIsNotAllowedToBeNull() {
        new SourceDirectory(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonexistingRootDirectoryResultsInException() {
        new SourceDirectory(new File("unexistingDirectory"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void nonDirectoryRootDirectoryResultsInException() throws IOException {
        new SourceDirectory(tempFolder.newFile());
    }

    @Test
    public void pathToEmptyDirectoryResultsInEmptyListOfFiles() {
        // Arrange
        final SourceDirectory emptyDirectory = new SourceDirectory(tempFolder.getRoot());

        // Act
        final List<File> files = emptyDirectory.getFiles();

        // Assert
        assertThat(files, is(not(nullValue())));
        assertThat(files.size(), is(equalTo(0)));
    }

    @Test
    public void rootDirectoryWithOneFileResultsInListOfThatOneFile() throws IOException {
        // Arrange
        tempFolder.newFile();
        final SourceDirectory nonEmptyDirectory = new SourceDirectory(tempFolder.getRoot());

        // Act
        final List<File> files = nonEmptyDirectory.getFiles();

        // Assert
        assertThat(files, is(not(nullValue())));
        assertThat(files.size(), is(equalTo(0)));
    }

    @Test
    public void rootDirectoryWithoutJavaFilesResultsInEmptyList() throws IOException {
        // Arrange
        tempFolder.newFile("Example.txt");
        final SourceDirectory nonExmptyDirectory = new SourceDirectory(tempFolder.getRoot());

        // Act
        final List<File> javaFiles = nonExmptyDirectory.getFiles();

        // Assert
        assertThat(javaFiles, is(not(nullValue())));
        assertThat(javaFiles.size(), is(equalTo(0)));
    }

    @Test
    public void rootDirectoryWithJavaFilesResultsInListOfThatOneJavaFile() throws IOException {
        // Arrange
        final File javaFile = tempFolder.newFile("Example.java");
        final SourceDirectory rootDirectory = new SourceDirectory(tempFolder.getRoot());

        // Act
        final List<File> javaFiles = rootDirectory.getFiles();

        // Assert
        assertThat(javaFiles, is(not(nullValue())));
        assertThat(javaFiles.size(), is(equalTo(1)));
        assertThat(javaFiles.get(0), is(javaFile));
    }

    @Test
    public void rootDirectoryWithDeeperNestedJavaFilesResultsInListOfThoseJavaFiles() throws IOException {
        // Arrange
        tempFolder.newFile("Example.java");
        val nested = new TemporaryFolder(tempFolder.getRoot());
        nested.create();
        nested.newFile("AnotherExample.java");
        val rootDirectory = new SourceDirectory(tempFolder.getRoot());

        // Act
        final List<File> javaFiles = rootDirectory.getFiles();

        // Assert
        assertThat(javaFiles, is(notNullValue()));
        assertThat(javaFiles.size(), is(equalTo(2)));
    }
}
