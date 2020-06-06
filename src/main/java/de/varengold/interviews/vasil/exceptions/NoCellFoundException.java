package de.varengold.interviews.vasil.exceptions;

/**
 * This exception is thrown when there isn't a Cell with the given columnName.
 */
public class NoCellFoundException extends RuntimeException {

    private static final long serialVersionUID = 6718828512143293558L;

    public NoCellFoundException(String message) {
        super(message);
    }

    public NoCellFoundException(Throwable cause) {
        super(cause);
    }
    public NoCellFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
