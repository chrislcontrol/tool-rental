package tool.rental.domain.controllers;

import tool.rental.domain.entities.Friend;
import tool.rental.domain.use_cases.RegisterToolUseCase;
import tool.rental.domain.use_cases.UpdateFriendUseCase;
import tool.rental.utils.Controller;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;

public class  UpdateFriendsController extends Controller {

        private final UpdateFriendUseCase updateFriendUseCase = new UpdateFriendUseCase();

        public UpdateFriendsController(PresentationFrame frame) {
            super(frame);
        }

        public void updateFriend(String name,  int phone, int social_security) throws ToastError {
            this.updateFriendUseCase.execute(name, phone, social_security);
            JOptionPane.showMessageDialog(null, "Amigo atualizado com sucesso!");
    }
}