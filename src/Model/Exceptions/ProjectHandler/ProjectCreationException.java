package Model.Exceptions.ProjectHandler;

/**
 * Exception thrown when creating a project fails.
 */
public class ProjectCreationException extends Exception {
    public ProjectCreationException(Exception e) {
        super(e);
    }
}
