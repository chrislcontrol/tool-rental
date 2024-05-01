package tool.rental.app;

import tool.rental.domain.entities.User;
import tool.rental.presentation.AppMainFrame;
import tool.rental.presentation.LoginFrame;
import tool.rental.utils.NotLogged;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

public class Settings {
    private static PresentationFrame PREVIOUS_FRAME = null;
    private static PresentationFrame FIRST_FRAME = new LoginFrame();
    private static User USER = null;

    public static User getUser() {
        if (USER == null || !USER.isAuthenticated()) {
            throw new RuntimeException(new NotLogged());
        }

        return USER;
    }

    public static void setUser(User user) {
        USER = user;
    }

    public static void setFirstFrameAsMain() throws ToastError {
        FIRST_FRAME = new AppMainFrame();
    }

    public static PresentationFrame getPreviousFrame() {
        return PREVIOUS_FRAME;
    }

    public static void setPreviousFrame(PresentationFrame previousFrame) {
        PREVIOUS_FRAME = previousFrame;
    }

    public static PresentationFrame getFirstFrame() {
        return FIRST_FRAME;
    }

    public static void setFirstFrame(PresentationFrame firstFrame) {
        FIRST_FRAME = firstFrame;
    }
}
