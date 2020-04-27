package Model.Exceptions;

/**
 * Exception when instanciating a new File Handler object
 */
public class FileHandlerConstructorException extends Exception {
    public FileHandlerConstructorException(Exception e) {
        super(e);
    }
}
