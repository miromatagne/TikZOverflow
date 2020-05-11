package Model.Exceptions.LatexHandler;

/**
 * Exception of compilation of the latex file
 */
public class LatexCompilationException extends Exception {

    public LatexCompilationException(Exception e) {
        super(e);
    }

    public LatexCompilationException() {

    }
}
