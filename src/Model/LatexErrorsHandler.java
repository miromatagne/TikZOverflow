package Model;

import Controller.Session;
import Model.Exceptions.LogErrorException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Handle the .Log file generated after a Latex compilation in order to extract the errors log
 */

public class LatexErrorsHandler {
    private static int ERRORS_COUNTER = 0;
    private static String ERRORS = "";

    /**
     * Get the errors in the compiler
     *
     * @return string with all the errors that the user let in the compiler
     */
    public String getErrors() {
        return ERRORS;
    }

    /**
     * Get the counter of errors in the compiler
     *
     * @return quantity of errors that occur in the compiler
     */
    public int getErrorsCounter() {
        return ERRORS_COUNTER;
    }

    /**
     * Find the errors that the user has written in the compiler
     *
     * @param path     the path to the log file which contains all information about the last compilation
     *                 that we made
     * @param username each error give the username so we need it to filter errors from other information
     * @throws LogErrorException If there was an error while reading the file.
     */
    public void errorLogs(String path, String username) throws LogErrorException {
        try {
            ERRORS = "";
            ERRORS_COUNTER = 0;
            File file = new File(path);
            String[] words;
            FileReader fileReader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(fileReader);
            String line;
            String input = Controller.Session.getInstance().getCurrentProject().getPath() + Session.getInstance().getCurrentProject().getTitle() + ".tex";

            while ((line = buffer.readLine()) != null) {
                words = line.split(":");
                for (String word : words) {
                    if (word.equals(input)) {
                        ERRORS_COUNTER++;
                        for (int i = 1; i < words.length; i++) {
                            if (i == 1) {
                                ERRORS += "line ";
                            }
                            ERRORS += words[i];
                            if (i < words.length - 1) {
                                ERRORS += ":";
                            } else {
                                ERRORS += "\n";
                            }
                        }
                    } else if (word.equals("*** (job aborted, no legal \\end found)")) {
                        ERRORS_COUNTER++;
                        ERRORS += "*** Missing '\\begin{document}' and/or '\\end{document}'";
                    }
                }
            }
        } catch (IOException e) {
            throw new LogErrorException(e);
        }
    }
}
