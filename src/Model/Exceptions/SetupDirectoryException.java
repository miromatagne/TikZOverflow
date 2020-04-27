package Model.Exceptions;

/**
 * Exception which occurs if a trouble happen while the directory setup
 */
public class SetupDirectoryException extends Exception {
    public SetupDirectoryException(Exception e) {
        super(e);
    }
}
