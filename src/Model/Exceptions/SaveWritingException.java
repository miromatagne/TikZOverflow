package Model.Exceptions;

/**
 * Exception which can happen during a save writing
 */
public class SaveWritingException extends Exception {
    public SaveWritingException(Exception e) {
        super(e);
    }
}
