package tool.rental.app;

import tool.rental.domain.entities.User;
import tool.rental.presentation.AppMainFrame;
import tool.rental.presentation.LoginFrame;
import tool.rental.utils.NotLogged;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

/**
 * A class that holds the application's settings.
 */
public class Settings {

    /**
     * The previous frame in the application's navigation history.
     */
    private static PresentationFrame PREVIOUS_FRAME = null;

    /**
     * The first frame of the application.
     */
    private static PresentationFrame FIRST_FRAME = new LoginFrame();

    /**
     * The current user of the application.
     */
    private static User USER = null;

    /**
     * Returns the current user of the application.
     *
     * @return the current user
     * @throws RuntimeException if the user is not logged in
     */
    public static User getUser() {
        if (USER == null ||!USER.isAuthenticated()) {
            throw new RuntimeException(new NotLogged());
        }

        return USER;
    }

    /**
     * Sets the current user of the application.
     *
     * @param user the new user
     */
    public static void setUser(User user) {
        USER = user;
    }

    /**
     * Sets the first frame of the application as the main frame.
     *
     * @throws ToastError if an error occurs
     */
    public static void setFirstFrameAsMain() throws ToastError {
        FIRST_FRAME = new AppMainFrame();
    }

    /**
     * Returns the previous frame in the application's navigation history.
     *
     * @return the previous frame
     */
    public static PresentationFrame getPreviousFrame() {
        return PREVIOUS_FRAME;
    }

    /**
     * Sets the previous frame in the application's navigation history.
     *
     * @param previousFrame the new previous frame
     */
    public static void setPreviousFrame(PresentationFrame previousFrame) {
        PREVIOUS_FRAME = previousFrame;
    }

    /**
     * Returns the first frame of the application.
     *
     * @return the first frame
     */
    public static PresentationFrame getFirstFrame() {
        return FIRST_FRAME;
    }

    /**
     * Sets the first frame of the application.
     *
     * @param firstFrame the new first frame
     */
    public static void setFirstFrame(PresentationFrame firstFrame) {
        FIRST_FRAME = firstFrame;
    }
}
