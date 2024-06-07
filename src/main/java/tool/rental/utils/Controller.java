package tool.rental.utils;

/**
 * Abstract class representing a controller in the application.
 * A controller manages the interaction between the user interface (presentation layer) and the application logic.
 */
public abstract class Controller {

    // The presentation frame associated with the controller
    public final PresentationFrame frame;

    /**
     * Constructs a new Controller with the specified presentation frame.
     *
     * @param frame the presentation frame associated with the controller
     */
    public Controller(PresentationFrame frame) {
        this.frame = frame;
    }
}

