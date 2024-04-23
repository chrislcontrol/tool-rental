package tool.rental.Domain.Controllers;

import tool.rental.Domain.UseCases.RegisterUserUseCase;
import tool.rental.Utils.Controller;
import tool.rental.Utils.PresentationFrame;
import tool.rental.Utils.ToastError;

import javax.swing.*;

public class RegisterUserController extends Controller {
    private final RegisterUserUseCase registerUserUseCase = new RegisterUserUseCase();

    public RegisterUserController(PresentationFrame frame) {
        super(frame);
    }

    public void registerUser(String username, String password, String confirmPassword) throws ToastError {
        this.registerUserUseCase.execute(username, password, confirmPassword);

        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");

        this.frame.setVisible(false);
    }
}
