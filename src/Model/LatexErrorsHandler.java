package Model;

import Controller.Session;
import Model.Exceptions.LatexErrorsHandler.LogErrorException;

import java.io.File;
import java.io.IOException;

/**
 * Handle the .Log file generated after a Latex compilation in order to extract the errors log
 */

public class LatexErrorsHandler extends FileHandler{
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
     * @throws LogErrorException If there was an error while reading the file.
     */
    public void errorLogs(String path) throws LogErrorException {
        try {
            ERRORS = "";
            ERRORS_COUNTER = 0;
            String[] linesLogFile = super.readInFile(path).split("\n");
            String[] words;
            String errorPrefix = Controller.Session.getInstance().getCurrentProject().getPath() + File.separator + Session.getInstance().getCurrentProject().getTitle() + ".tex";

            for (String s : linesLogFile) {
                words = s.split(":");
                for (String word : words) {
                    if (word.equals(errorPrefix)) {
                        ERRORS_COUNTER++;
                        for (int j = 1; j < words.length; j++) {
                            if (j == 1) {
                                ERRORS += "line ";
                            }
                            ERRORS += words[j];
                            if (j < words.length - 1) {
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
