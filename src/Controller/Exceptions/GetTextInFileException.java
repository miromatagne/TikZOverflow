package Controller.Exceptions;

import java.io.IOException;

public class GetTextInFileException extends Exception {
    public GetTextInFileException(Exception e) {
        super(e);
    }
}
