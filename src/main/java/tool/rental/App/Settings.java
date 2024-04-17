package tool.rental.App;

import tool.rental.Domain.Entities.User;
import tool.rental.Presentation.AppMainFrame;
import tool.rental.Presentation.LoginFrame;
import tool.rental.Utils.NotLogged;
import tool.rental.Utils.PresentationFrame;
import tool.rental.Utils.ToastError;

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
