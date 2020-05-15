package Model.Exceptions.ProjectHandler;

/**
 * Exception of writing in a .tex file
 */
public class LatexWritingException extends Exception {
    public LatexWritingException(Exception e) {
        super(e);
    }
}
