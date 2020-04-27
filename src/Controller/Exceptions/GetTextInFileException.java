package Controller.Exceptions;

/**
 * Exception which occurs when the code source can not be get from the save file
 */
public class GetTextInFileException extends Exception {
    public GetTextInFileException(Exception e) {
        super(e);
    }
}
