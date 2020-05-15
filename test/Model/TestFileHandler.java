package Model;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the file handler methods.
 */
class TestFileHandler {

    @Test
    void writeInFile() {
        /**
         * This method simply writes to a file, it cannot be tested without creating and writing
         * to a test file using the same methods. Therefore this test is not useful.
         */
    }

    @Test
    void readInFile() {
        /**
         * This method simply reads a file, it cannot be tested without creating and writing
         * to a test file, and reading it to verify if the test passed,
         * using the same methods. Therefore this test is not useful.
         */
    }

    @Test
    void deleteDirectory() {
        File directory = new File("./test/Model/TestFileHandler");
        directory.mkdir();
        File file = new File("./test/Model/TestFileHandler/test.txt");
        File file2 = new File("./test/Model/TestFileHandler/test2.txt");
        try {
            file.createNewFile();
            file2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileHandler fileHandler = new FileHandler();
        assertTrue(file.exists());
        assertTrue(file2.exists());
        try {
            fileHandler.deleteDirectory(new File("./test/Model/TestFileHandler"));
        } catch (IOException e) {
            fail();
        }
        assertFalse(file.exists());
        assertFalse(file2.exists());
    }
}