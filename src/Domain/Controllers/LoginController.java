package tool.rental.Domain.Controllers;

import tool.rental.Domain.Entities.User;
import tool.rental.Domain.LoginUseCase;
import tool.rental.Presentation.LoginFrame;
import tool.rental.Utils.Controller;
import tool.rental.Utils.PresentationFrame;
import tool.rental.Utils.ToastError;

public class LoginController extends Controller {
    private final LoginUseCase loginUseCase = new LoginUseCase();

    public LoginController(PresentationFrame frame) {
        super(frame);
    }

    public void execute(String username, String password, boolean rememberMe) throws ToastError {
        User user = this.loginUseCase.execute(username, password, rememberMe);
        this.frame.swapFrame(new LoginFrame());
    }
}