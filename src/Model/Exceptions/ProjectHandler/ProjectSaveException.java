package Model.Exceptions.ProjectHandler;

/**
 * Exception thrown when saving a project fails.
 */
public class ProjectSaveException extends Exception {
    public ProjectSaveException(Exception e) {
        super(e);
    }
}
