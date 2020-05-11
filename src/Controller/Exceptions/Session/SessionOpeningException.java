package Controller.Exceptions.Session;

/**
 * Exception while opening a session
 */
public class SessionOpeningException extends Exception {
    public SessionOpeningException(Exception e) {
        super(e);
    }
}
