package Controller.Exceptions;

import Model.Exceptions.UserFromSaveCreationException;

public class SessionOpeningException extends Exception {
    public SessionOpeningException(Exception e) {
        super(e);
    }
}
