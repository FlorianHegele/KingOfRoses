package utils;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    private static final String validPath = "testFile.txt";;
    private static final String invalidPath = "invalidFile.txt";;

    @Test
    void testGetFileFromResources_validPath() {
        File file = FileUtils.getFileFromResources(validPath);
        assertNotNull(file);
        assertTrue(file.exists());
    }

    @Test
    void testGetFileFromResources_invalidPath() {
        assertThrows(IllegalArgumentException.class, () -> FileUtils.getFileFromResources(invalidPath));
    }

    @Test
    void testGetOuputStreamFromResources_validPath() {
        FileInputStream fis = FileUtils.getOuputStreamFromResources(validPath);
        assertNotNull(fis);
    }

    @Test
    void testGetOuputStreamFromResources_invalidPath() {
        assertThrows(IllegalArgumentException.class, () -> FileUtils.getOuputStreamFromResources(invalidPath));
    }
}
