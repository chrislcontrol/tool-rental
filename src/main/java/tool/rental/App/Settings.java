package tool.rental.App;

import tool.rental.Domain.Entities.User;
import tool.rental.Presentation.LoginFrame;
import tool.rental.Utils.NotLogged;
import tool.rental.Utils.PresentationFrame;

public class Settings {
    public static PresentationFrame PREVIOUS_FRAME = null;
    public static PresentationFrame FIRST_FRAME = new LoginFrame();
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

}
