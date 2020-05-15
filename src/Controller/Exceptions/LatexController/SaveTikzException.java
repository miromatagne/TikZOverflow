package Controller.Exceptions.LatexController;

/**
 * Error which occurs while saving TikZ code in a tex file
 */
public class SaveTikzException extends Exception {
    public SaveTikzException(Exception e) {
        super(e);
    }
}
