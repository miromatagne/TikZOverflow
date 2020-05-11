package Model.Exceptions.UserHandler;

/**
 * Exception occurs when there is a problem while creating a new user from a save
 */
public class UserFromSaveCreationException extends Exception {
    public UserFromSaveCreationException(Exception e) {
        super(e);
    }
}
