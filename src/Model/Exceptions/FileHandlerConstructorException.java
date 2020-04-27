package Model.Exceptions;
/**
 * Exception occurs when there is a problem with a FileHandler instance creation
 */
public class FileHandlerConstructorException extends Exception {
    public FileHandlerConstructorException(Exception e) {
        super(e);
    }
}
