package Model.Exceptions;

import java.io.FileNotFoundException;

public class LogErrorException extends Exception {
    public LogErrorException(Exception e) {
        super(e);
    }
}
