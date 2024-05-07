package tool.rental.domain.controllers;

import tool.rental.domain.use_cases.RegisterUserUseCase;
import tool.rental.utils.Controller;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

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
