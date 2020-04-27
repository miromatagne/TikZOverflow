package Controller.Exceptions;

/**
 * Exception corresponding to an error while building the code from the shapes
 */
public class BuildFullCodeFromShapesOnlyException extends Exception {
    public BuildFullCodeFromShapesOnlyException(Exception e) {
        super(e);
    }
}
