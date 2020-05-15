package Model.Exceptions;

/**
 * Exception occurs when a user tries to create an account with a username that is already taken.
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException() {

    }
}
