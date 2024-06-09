package tool.rental.domain.controllers;

import tool.rental.domain.use_cases.RegisterUserUseCase;
import tool.rental.utils.Controller;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.JOptionPane;

/**
 * The controller for registering a user.
 */
public class RegisterUserController extends Controller {

    /**
     * The use case to register a user.
     */
    private final RegisterUserUseCase registerUserUseCase = new RegisterUserUseCase();

    /**
     * Creates a new instance of the RegisterUserController.
     *
     * @param frame the presentation frame
     */
    public RegisterUserController(PresentationFrame frame) {
        super(frame);
    }

    /**
     * Registers a user.
     *
     * @param username the username
     * @param password the password
     * @param confirmPassword the confirmation of the password
     * @throws ToastError if an error occurs
     */
    public void registerUser(String username, String password, String confirmPassword) throws ToastError {
        this.registerUserUseCase.execute(username, password, confirmPassword);

        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");

        closeFrame();
    }

    /**
     * Closes the frame.
     */
    public void closeFrame() {
        frame.setVisible(false);
    }
}
