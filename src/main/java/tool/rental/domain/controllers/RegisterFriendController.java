package tool.rental.domain.controllers;

import tool.rental.domain.use_cases.RegisterFriendUseCase;
import tool.rental.utils.Controller;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;

public class RegisterFriendController extends Controller {
    private final RegisterFriendUseCase registerFriendUseCase = new RegisterFriendUseCase();

    public RegisterFriendController(PresentationFrame frame) {
        super(frame);
    }

    public void registerFriend(String name, String phone, String social_security, Runnable callback) throws ToastError {
        this.registerFriendUseCase.execute(name, phone, social_security);
        JOptionPane.showMessageDialog(null, "Amigo(a) cadastrado(a) com sucesso!");

        callback.run();

        closeFrame();
    }

    public void closeFrame() {
        frame.setVisible(false);
    }
}

