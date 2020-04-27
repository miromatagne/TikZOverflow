package Model.Exceptions;

/**
 * Exception while instancing a new File Handler object
 */
public class FileHandlerConstructorException extends Exception {
    public FileHandlerConstructorException(Exception e) {
        super(e);
    }
}
