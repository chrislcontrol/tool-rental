package tool.rental.Domain.Controllers;

import tool.rental.App.Settings;
import tool.rental.Domain.Entities.User;
import tool.rental.Domain.UseCases.CreateMockUseCase;
import tool.rental.Domain.UseCases.LoginUseCase;
import tool.rental.Presentation.AppMainFrame;
import tool.rental.Presentation.RegisterUserFrame;
import tool.rental.Utils.Controller;
import tool.rental.Utils.JOptionPaneUtils;
import tool.rental.Utils.PresentationFrame;
import tool.rental.Utils.ToastError;

import javax.swing.*;

public class LoginController extends Controller {
    private final LoginUseCase loginUseCase = new LoginUseCase();
    private final CreateMockUseCase createMockUseCase = new CreateMockUseCase();

    public LoginController(PresentationFrame frame) {
        super(frame);
    }

    public void login(String username, String password, boolean rememberMe) throws ToastError {
        User user = this.loginUseCase.execute(username, password, rememberMe);
        Settings.setUser(user);

        if (!user.hasMock) {
            this.askToCreateMock(user);
        }

        this.frame.swapFrame(new AppMainFrame());
    }

    private void askToCreateMock(User user) throws ToastError {
        int option = JOptionPaneUtils.showInputYesOrNoDialog(
                "Usuário não possui dados mockados criados. Gostaria de criar?",
                "Criação de mock"
        );
        if (option == JOptionPane.YES_OPTION) {
            this.createMockUseCase.execute(user);
        }

    }

    public void openRegisterModal() {
        this.frame.swapFrame(new RegisterUserFrame(), true);
    }
}
