package tool.rental.App;

import tool.rental.Domain.Entities.User;
import tool.rental.Presentation.LoginFrame;
import tool.rental.Utils.PresentationFrame;

public class Settings {
    public static PresentationFrame PREVIOUS_FRAME = null;
    public static PresentationFrame FIRST_FRAME = new LoginFrame();
    public static User USER = null;
}
