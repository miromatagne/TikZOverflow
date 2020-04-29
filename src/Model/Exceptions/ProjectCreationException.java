package Model.Exceptions;

import java.io.IOException;

public class ProjectCreationException extends Exception {
    public ProjectCreationException(Exception e) {
        super(e);
    }
}
