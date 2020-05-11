package Model.Exceptions.UserHandler;

/**
 * Exception which occurs when we try to save a user
 */
public class SaveUserException extends Exception {
    public SaveUserException(Exception e) {
        super(e);
    }
}
