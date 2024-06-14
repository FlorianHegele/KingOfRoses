package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class FileUtils {

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


    public static FileInputStream getOuputStreamFromResources(String path) {
        try {
            return new FileInputStream(getFileFromResources(path));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
