package Controller.Exceptions;

/**
 * Exception while opening a session
 */
public class SessionOpeningException extends Exception {
    public SessionOpeningException(Exception e) {
        super(e);
    }
}
