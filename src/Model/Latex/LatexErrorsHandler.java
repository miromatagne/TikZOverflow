package Model.Latex;

import Controller.Session;
import Model.Exceptions.LatexErrorsHandler.LogErrorException;
import Model.FileHandler;

import java.io.File;
import java.io.IOException;

/**
 * Handle the .Log file generated after a Latex compilation in order to extract the errors log
 */

public class LatexErrorsHandler extends FileHandler {
    private static int ERRORS_COUNTER = 0;
    private static String ERRORS = "";
    private String[] linesLogFile;
    private String syntaxErrorPrefix;
    private String moduleErrorPrefix = "! LaTeX Error";
    private String documentError= "*** (job aborted, no legal \\end found)";

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
     * build a String that allows to identify the errors in the .log file
     * @return          the global prefix of an error message in the .log file
     */
    public String generalErrorsPrefix(){
        String[] allDirectories;
        String path = Controller.Session.getInstance().getCurrentProject().getPath() + File.separator + Session.getInstance().getCurrentProject().getTitle() + ".tex";
        allDirectories = path.split("\\\\");
        String errorsLogPath = "";
        for(int i = 0; i < allDirectories.length; i++){
            errorsLogPath+=allDirectories[i];
            if(i<allDirectories.length-1){
                errorsLogPath+="/";
            }else {
                errorsLogPath+=":";
            }
        }
        return errorsLogPath;
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
            linesLogFile = super.readInFile(path).split("\n");
            syntaxErrorPrefix = generalErrorsPrefix();
            addErrors();
        } catch (IOException e) {
            throw new LogErrorException(e);
        }
    }

    /**
     * An error in the log file can be written on multiple lines and this method manage to catch errors and add if the
     * error is on multiple lines
     */
    private void addErrors(){
        boolean lineIsError = false;
        for (String s : linesLogFile) {
            if (lineIsError) {
                lineIsError = addErrorLine(s);

            } else {
                if (s.equals(documentError)) {
                    ERRORS_COUNTER++;
                    ERRORS += documentError;
                } else {
                    lineIsError = catchError(s);
                }
            }
        }
    }

    /**
     * this method looks for errors in the .log file
     * @param line          line to check if it is an error message
     * @return TRUE if the line is an error message
     *         FALSE otherwise
     */
    private boolean catchError(String line) {
        String[] words = line.split(":");
        String[] toAddError;
        String catchFile = "";
        boolean lineIsError = false;
        int i=0;
        while (i<words.length-1) {
            catchFile += words[i];
            catchFile += ":";
            if (catchFile.equals(syntaxErrorPrefix) || words[i].equals(moduleErrorPrefix)) {
                lineIsError = true;
                ERRORS_COUNTER++;
                if(catchFile.equals(syntaxErrorPrefix)){
                    ERRORS += "line ";
                    toAddError = line.split(syntaxErrorPrefix);
                }
                else{
                    toAddError = line.split(moduleErrorPrefix);
                    toAddError = toAddError[1].split(":");
                }
                ERRORS += toAddError[1];
                if(toAddError[1].charAt(toAddError[1].length()-1) == '.'){
                    lineIsError = false;
                    ERRORS+="\n";
                }
                i=words.length;
            }
            i++;
        }
        return lineIsError;
    }

    /**
     * Add a full line from log file to error
     * @param line         the line to add
     * @return TRUE if the line is an error message
     *         FALSE otherwise
     */
    private boolean addErrorLine(String line) {
        boolean lineIsError = true;
        ERRORS += line;
        if(line.charAt(line.length()-1) == '.'){
            ERRORS += "\n";
            lineIsError=false;
        }
        return lineIsError;
    }
}
