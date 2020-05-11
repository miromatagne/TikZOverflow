package Model.Exceptions.ProjectHandler;

/**
 * Exception thrown when generating a project from a save file fails.
 */
public class ProjectFromSaveGenerationException extends Exception {
    public ProjectFromSaveGenerationException(Exception e) {
        super(e);
    }
}
