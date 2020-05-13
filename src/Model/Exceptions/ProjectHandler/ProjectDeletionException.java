package Model.Exceptions.ProjectHandler;

/**
 * Exception thrown when deleting a project fails.
 */
public class ProjectDeletionException extends Exception {
    public ProjectDeletionException(Exception e){
        super(e);
    }

}
