package Controller.Exceptions;

import Model.Exceptions.SetupDirectoryException;

public class LatexControllerConstructorException extends Exception {
    public LatexControllerConstructorException(Exception e) {
        super(e);
    }
}
