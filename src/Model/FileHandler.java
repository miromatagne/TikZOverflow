package Model;

import java.io.*;

public class FileHandler {
    /**
     * Writes text into file.
     *
     * @param file File written
     * @param text Content to write
     * @throws IOException when a IO error occurs
     */
    public void writeInFile(File file, String text) throws IOException {
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(text);
        bw.close();
        fw.close();
    }

    /**
     * Read the text in a File
     *
     * @param file file.
     * @return text in the file
     * @throws IOException if error in IO interactions
     */
    public String readInFile(File file) throws IOException {
        String textToRead;
        StringBuilder builder = new StringBuilder();

        FileReader reader = new FileReader(file);
        BufferedReader buffer = new BufferedReader(reader);
        while ((textToRead = buffer.readLine()) != null) {
            builder.append(textToRead).append("\n");
        }
        buffer.close();
        reader.close();
        return builder.toString();
    }
}
