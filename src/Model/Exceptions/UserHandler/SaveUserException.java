package Model.Exceptions.UserHandler;

/**
 * Exception which can occur when we try to save a user
 */
public class SaveUserException extends Exception {
    public SaveUserException(Exception e) {
        super(e);
    }
}
