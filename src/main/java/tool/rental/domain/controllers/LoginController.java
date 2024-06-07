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

import javax.swing.JOptionPane;

/**
 * The controller for the login.
 */
public class LoginController extends Controller {

    /**
     * The use case to login.
     */
    private final LoginUseCase loginUseCase = new LoginUseCase();

    /**
     * The use case to create a mock.
     */
    private final CreateMockUseCase createMockUseCase = new CreateMockUseCase();

    /**
     * Creates a new instance of the LoginController.
     *
     * @param frame the presentation frame
     */
    public LoginController(PresentationFrame frame) {
        super(frame);
    }

    /**
     * Logs in a user.
     *
     * @param username the username
     * @param password the password
     * @param rememberMe whether to remember the user
     * @throws ToastError if an error occurs
     */
    public void login(String username, String password, boolean rememberMe) throws ToastError {
        User user = this.loginUseCase.execute(username, password, rememberMe);
        Settings.setUser(user);

        if (!user.hasMock) {
            this.askToCreateMock(user);
        }

        this.frame.swapFrame(new AppMainFrame());
    }

    /**
     * Asks the user to create a mock.
     *
     * @param user the user
     * @throws ToastError if an error occurs
     */
    private void askToCreateMock(User user) throws ToastError {
        int option = JOptionPaneUtils.showInputYesOrNoDialog(
                "Usuário não possui dados mockados criados. Gostaria de criar?",
                "Criação de mock"
        );
        if (option == JOptionPane.YES_OPTION) {
            this.createMockUseCase.execute(user);
        }

    }

    /**
     * Opens the register user modal.
     */
    public void openRegisterModal() {
        this.frame.swapFrame(new RegisterUserFrame(), true);
    }
}
