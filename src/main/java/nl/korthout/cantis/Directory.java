package nl.korthout.cantis;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;

import lombok.NonNull;

/**
 * A directory of files.
 */
public interface Directory {

    /**
     * Provides the files in this directory.
     * @return the files
     */
    Collection<File> files();

    /**
     * A directory of source files.
     */
    final class FromSource implements Directory {

        private static final String[] EXTENSIONS = new String[]{"java"};

        private final Collection<File> sources;

        /**
         * Constructor.
         * @param path Path to the directory containing the source files
         */
        FromSource(String path) {
            this(new File(path));
        }

        /**
         * Constructor.
         * @param directory Directory containing the source files
         */
        FromSource(@NonNull File directory) {
            if (!directory.isDirectory()) {
                throw new IllegalArgumentException("not a directory");
            }
            this.sources = FileUtils.listFiles(directory, EXTENSIONS, true);
        }

        @Override
        public Collection<File> files() {
            return sources;
        }
    }
}
