package Model.Exceptions;

import java.io.IOException;

public class SaveWritingException extends Exception {
    public SaveWritingException(Exception e) {
        super(e);
    }
}
