package Model.Exceptions.ProjectHandler;

/**
 * Exception thrown when renaming a project fails.
 */
public class ProjectRenameException extends Exception {
    public ProjectRenameException(){
        super();
    }

    public ProjectRenameException(Exception e){
        super(e);
    }
}
