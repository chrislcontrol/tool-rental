package tool.rental.domain.controllers;

import tool.rental.app.Settings;
import tool.rental.domain.entities.User;
import tool.rental.domain.use_cases.CreateMockUseCase;
import tool.rental.domain.use_cases.LoginUseCase;
import tool.rental.presentation.AppMainFrame;
import tool.rental.presentation.RegisterUserFrame;
import tool.rental.utils.Controller;
import tool.rental.utils.JOptionPaneUtils;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

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
