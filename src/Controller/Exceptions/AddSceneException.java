package Controller.Exceptions;

/**
 * Exception corresponding to an error while adding a scene in a stage
 */
public class AddSceneException extends Exception {
    public AddSceneException(Exception e) {
        super(e);
    }
}
