package Controller.Exceptions.LatexController;

/**
 * Exception when the creation of the image from the PDF failed
 */
public class CreationImageFromPDFException extends Exception {
    public CreationImageFromPDFException(Exception e) {
        super(e);
    }
}
