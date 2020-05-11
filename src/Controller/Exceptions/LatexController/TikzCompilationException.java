package Controller.Exceptions.LatexController;

/**
 * Exception which occurs if there is a problem while the Tikz compilation
 */
public class TikzCompilationException extends Exception {
    public TikzCompilationException(Exception e) {
        super(e);
    }
}
