package Model.Latex;

import Controller.Session;
import Model.Exceptions.LatexErrorsHandler.LogErrorException;
import Model.FileHandler;

import java.io.File;
import java.io.IOException;

/**
 * Handle the .Log file generated after a Latex compilation in order to extract the errors log.
 */

public class LatexErrorsHandler extends FileHandler {
    private  int errorsCounter = 0;
    private String errors = "";
    private String[] linesLogFile;
    private String syntaxErrorPrefix;
    private final static String MODULE_ERROR_PREFIX = "! LaTeX Error";
    private final static String DOCUMENT_ERROR = "*** (job aborted, no legal \\end found)";

    /**
     * Get the errors in the compiler.
     *
     * @return string with all the errors that the user let in the compiler
     */
    public String getErrors() {
        return errors;
    }

    /**
     * Get the counter of errors in the compiler.
     *
     * @return quantity of errors that occur in the compiler
     */
    public int getErrorsCounter() {
        return errorsCounter;
    }

    /**
     * Build a String that allows to identify the errors in the .log file.
     *
     * @return the global prefix of an error message in the .log file
     */
    private String formatPath() {
        String[] allDirectories;
        String path = Controller.Session.getInstance().getCurrentProject().getPath() + File.separator + Session.getInstance().getCurrentProject().getTitle() + ".tex";
        allDirectories = path.split("\\\\");
        String errorsLogPath = "";
        for(int i = 0; i < allDirectories.length; i++) {
            errorsLogPath+=allDirectories[i];
            if(i<allDirectories.length-1){
                errorsLogPath+="/";
            } else {
                errorsLogPath+=":";
            }
        }
        return errorsLogPath;
    }

    /**
     * Find the errors that the user has written in the compiler.
     *
     * @param path     the path to the log file which contains all information about the last compilation
     *                 that we made
     * @throws LogErrorException If there was an error while reading the file.
     */
    public void errorLogs(String path) throws LogErrorException {
        try {
            errors = "";
            errorsCounter = 0;
            linesLogFile = super.readInFile(path).split("\n");
            syntaxErrorPrefix = formatPath();
            addErrors();
        } catch (IOException e) {
            throw new LogErrorException(e);
        }
    }

    /**
     * An error in the log file can be written on multiple lines and this method manage to catch errors and add if the
     * error is on multiple lines.
     */
    private void addErrors() {
        boolean lineIsError = false;
        for (String s : linesLogFile) {
            if (lineIsError) {
                lineIsError = addErrorLine(s);

            } else {
                if (s.equals(DOCUMENT_ERROR)) {
                    errorsCounter++;
                    errors += DOCUMENT_ERROR;
                } else {
                    lineIsError = formatErrors(s);
                }
            }
        }
    }

    /**
     * Looks for errors in the .log file, and formats it to be shown to the user.
     *
     * @param line          line to check if it is an error message
     * @return TRUE if the line is an error message
     *         FALSE otherwise
     */
    private boolean formatErrors(String line) {
        String[] words = line.split(":");
        String[] toAddError;
        String catchFile = "";
        boolean lineIsError = false;
        int i=0;
        while (i<words.length-1) {
            catchFile += words[i];
            catchFile += ":";
            if (catchFile.equals(syntaxErrorPrefix) || words[i].equals(MODULE_ERROR_PREFIX)) {
                lineIsError = true;
                errorsCounter++;
                if(catchFile.equals(syntaxErrorPrefix)){
                    errors += "line ";
                    toAddError = line.split(syntaxErrorPrefix);
                }
                else{
                    toAddError = line.split(MODULE_ERROR_PREFIX);
                    toAddError = toAddError[1].split(":");
                }
                errors += toAddError[1];
                if(toAddError[1].charAt(toAddError[1].length()-1) == '.') {
                    lineIsError = false;
                    errors +="\n";
                }
                i=words.length;
            }
            i++;
        }
        return lineIsError;
    }

    /**
     * Add a full line from log file to error.
     *
     * @param line         the line to add
     * @return TRUE if the line is an error message
     *         FALSE otherwise
     */
    private boolean addErrorLine(String line) {
        boolean lineIsError = true;
        errors += line;
        if(line.charAt(line.length()-1) == '.') {
            errors += "\n";
            lineIsError=false;
        }
        return lineIsError;
    }
}
