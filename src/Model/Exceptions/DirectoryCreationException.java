package Model.Exceptions;
/**
 * Exception when creating a new directory
 */
public class DirectoryCreationException extends Exception {
    public DirectoryCreationException(){
        super();
    }

    public DirectoryCreationException(Exception e){
        super(e);
    }
}
