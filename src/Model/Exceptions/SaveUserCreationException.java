package Model.Exceptions;

/**
 * Exception which can happen during the creation of a save user
 */
public class SaveUserCreationException extends Exception {
    public SaveUserCreationException(Exception e) {
        super(e);
    }
}
