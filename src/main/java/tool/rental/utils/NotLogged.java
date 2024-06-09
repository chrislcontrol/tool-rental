package tool.rental.utils;

/**
 * Custom exception class representing the situation where a user is not logged in.
 */
public class NotLogged extends Exception {
    /**
     * Constructs a new NotLogged exception with no detail message.
     */
    public NotLogged() {
        super();
    }

    /**
     * Constructs a new NotLogged exception with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method)
     */
    public NotLogged(String message) {
        super(message);
    }
}

