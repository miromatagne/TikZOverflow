package Model;

import java.io.*;

/**
 * This class is used to handle interactions with files
 */

public class FileHandler {
    /**
     * Writes text into file.
     *
     * @param file File written
     * @param text Content to write
     * @throws IOException when a IO error occurs
     */
    public void writeInFile(File file, String text) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            bw.write(text);
        } catch (IOException e){
            throw e;
        }
    }

    /**
     * Read the text in a File
     *
     * @param path file.
     * @return text in the file
     * @throws IOException if error in IO interactions
     */
    public String readInFile(String path) throws IOException {
        try (BufferedReader buffer = new BufferedReader(new FileReader(new File(path)))){
            String textInFile;
            StringBuilder builder = new StringBuilder();
            while ((textInFile = buffer.readLine()) != null) {
                builder.append(textInFile).append("\n");
            }

            return builder.toString();
        } catch (IOException e){
            throw e;
        }

    }

    void deleteDirectory(File file) throws IOException {
        if (file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    System.out.println(entry.getPath());
                    deleteDirectory(entry);
                }
            }
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete " + file);
        }
    }
}
