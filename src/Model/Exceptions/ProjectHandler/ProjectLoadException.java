package Model.Exceptions.ProjectHandler;

/**
 * Exception thrown when loading a project fails.
 */
public class ProjectLoadException extends Exception {
    public ProjectLoadException(Exception e) {
        super(e);
    }
}
