package de.varengold.interviews.vasil.exceptions;

/**
 * InvalidSheetException notifies the user that the given sheet is null(doesn't exist).
 * @author VVasilev
 */
public class InvalidSheetException extends RuntimeException{

    private static final long serialVersionUID = 7718828512143293558L;

    public InvalidSheetException(String message) {
        super(message);
    }

    public InvalidSheetException(Throwable cause) {
        super(cause);
    }
    public InvalidSheetException(String message, Throwable cause) {
        super(message, cause);
    }
}
