package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Utility class for file operations related to resource files.
 */
public class FileUtils {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private FileUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Retrieves a file from the resources folder.
     *
     * @param path the relative path to the resource file.
     * @return the File object representing the resource file.
     * @throws IllegalArgumentException if the file is not found.
     */
    public static File getFileFromResources(String path) {
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        URL resource = classLoader.getResource(path);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + path);
        } else {
            try {
                return new File(resource.toURI());
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Retrieves a FileInputStream for a file from the resources folder.
     *
     * @param path the relative path to the resource file.
     * @return the FileInputStream for the resource file.
     * @throws RuntimeException if the file is not found or cannot be read.
     */
    public static FileInputStream getOuputStreamFromResources(String path) {
        try {
            return new FileInputStream(getFileFromResources(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
