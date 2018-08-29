package nl.korthout.cantis;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
public class Sources {
    private static final String[] EXTENSIONS = new String[]{ "java" };

    private final List<File> files;

    public Sources(@NonNull String rootDirectory) {
        this(new File(rootDirectory));
    }

    public Sources(@NonNull File rootDirectory) {
        checkArgument(rootDirectory.isDirectory());
        final File[] filesArray = rootDirectory.listFiles();
        if (filesArray == null) {
            throw new IllegalArgumentException("Unknown path");
        }
        this.files = new ArrayList<>(FileUtils.listFiles(rootDirectory, EXTENSIONS, true));
    }

    public int getNumberOfFiles() {
        return files.size();
    }
}
