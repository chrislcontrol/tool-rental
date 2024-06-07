package tool.rental.domain.controllers;

import tool.rental.domain.entities.User;
import tool.rental.domain.use_cases.UpdateFriendUseCase;
import tool.rental.utils.Controller;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.JOptionPane;


/**
 * The controller for updating a friend.
 */
public class UpdateFriendsController extends Controller {

    /**
     * The use case to update a friend.
     */
    private final UpdateFriendUseCase updateFriendUseCase = new UpdateFriendUseCase();

    /**
     * Creates a new instance of the UpdateFriendsController.
     *
     * @param frame the presentation frame
     */
    public UpdateFriendsController(PresentationFrame frame) {
        super(frame);
    }

    /**
     * Updates a friend.
     *
     * @param id the id of the friend to update
     * @param name the new name of the friend
     * @param phone the new phone number of the friend
     * @param social_security the new social security number of the friend
     * @param user the user performing the update
     * @throws ToastError if an error occurs
     */
    public void updateFriend(String id, String name, String phone, String social_security, User user) throws ToastError {
        this.updateFriendUseCase.execute(id, name, phone, social_security, user);
        JOptionPane.showMessageDialog(null, "Amigo atualizado com sucesso!");
        this.closeFrame();
    }

    /**
     * Closes the frame.
     */
    public void closeFrame() {
        frame.setVisible(false);
    }
}

