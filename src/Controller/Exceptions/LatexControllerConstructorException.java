package Controller.Exceptions;

/**
 * Exception corresponding to an error while instancing a new Latex Controller object
 */
public class LatexControllerConstructorException extends Exception {
    public LatexControllerConstructorException(Exception e) {
        super(e);
    }
}
