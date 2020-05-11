package Model.Exceptions.LatexErrorsHandler;

/**
 * Exception of writing the log error of a tex file compilation
 */
public class LogErrorException extends Exception {
    public LogErrorException(Exception e) {
        super(e);
    }
}
