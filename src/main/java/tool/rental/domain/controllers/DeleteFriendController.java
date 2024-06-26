package tool.rental.domain.controllers;

import tool.rental.domain.entities.Friend;
import tool.rental.domain.repositories.FriendRepository;
import tool.rental.domain.use_cases.DeleteFriendUseCase;
import tool.rental.utils.Controller;
import tool.rental.utils.JOptionPaneUtils;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.JOptionPane;

/**
 * The controller for deleting a friend.
 */
public class DeleteFriendController extends Controller {
    private final DeleteFriendUseCase deleteFriendUseCase = new DeleteFriendUseCase();
    private FriendRepository friendsRepository = new FriendRepository();

    public DeleteFriendController(PresentationFrame frame) {
        super(frame);
    }

    /**
     * Deletes a friend.
     *
     * @param friendId the ID of the friend to delete
     * @throws ToastError if an error occurs
     */
    public void deleteFriend(String friendId) throws ToastError {
        Friend friend = this.friendsRepository.getById(friendId);

        int userOption = JOptionPaneUtils.showInputYesOrNoDialog(
                "Tem certeza que deseja deletar este amigo?",
                "Deletar Amigo"
        );

        if (userOption != JOptionPane.YES_OPTION) {
            return;
        }

        this.deleteFriendUseCase.execute(friendId);

        JOptionPane.showMessageDialog(
                null,
                "Amigo deletado com sucesso."
        );
    }
}
