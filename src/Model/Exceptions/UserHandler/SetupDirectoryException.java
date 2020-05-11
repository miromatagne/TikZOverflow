package Model.Exceptions.UserHandler;
/**
 * Exception occurs when there is a problem while setting up a directory
 */
public class SetupDirectoryException extends Exception {
    public SetupDirectoryException(Exception e) {
        super(e);
    }
}
