package Model.Exceptions.ProjectHandler;

/**
 * Exception thrown when copying a project fails.
 */
public class ProjectCopyException extends Throwable {
    public ProjectCopyException(Exception e) {
        super(e);
    }
}
