package Model.Exceptions;

import java.io.IOException;

public class UserFromSaveCreationException extends Exception {
    public UserFromSaveCreationException(Exception e) {
        super(e);
    }
}
