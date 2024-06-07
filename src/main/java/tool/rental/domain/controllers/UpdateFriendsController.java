package tool.rental.domain.controllers;

import tool.rental.domain.entities.User;
import tool.rental.domain.use_cases.UpdateFriendUseCase;
import tool.rental.utils.Controller;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;

public class UpdateFriendsController extends Controller {

    private final UpdateFriendUseCase updateFriendUseCase = new UpdateFriendUseCase();

    public UpdateFriendsController(PresentationFrame frame) {
        super(frame);
    }

    public void updateFriend(String id, String name, String phone, String social_security, User user) throws ToastError {
        this.updateFriendUseCase.execute(id, name, phone, social_security, user);
        JOptionPane.showMessageDialog(null, "Amigo atualizado com sucesso!");
        this.closeFrame();
    }

    public void closeFrame() {
        frame.setVisible(false);
    }
}

