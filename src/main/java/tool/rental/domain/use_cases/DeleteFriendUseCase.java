package tool.rental.domain.use_cases;

import tool.rental.domain.repositories.FriendRepository;
import tool.rental.utils.ToastError;

/**
 * This class represents a use case for deleting a friend from the system.
 */
public class DeleteFriendUseCase {
    private final FriendRepository friendRepository = new FriendRepository(); // Repository for friends

    /**
     * Executes the use case to delete a friend from the system.
     *
     * @param friendId The ID of the friend to be deleted.
     * @throws ToastError if an error occurs during the execution or if the friend has a tool rented.
     */
    public void execute(String friendId) throws ToastError {
        // Check if the friend has a tool rented
        if (friendRepository.friendHasToolRented(friendId)) {
            throw new ToastError(
                    "Não foi possível deletar o amigo pois o mesmo possui uma ferramenta em empréstimo.",
                    "Erro ao deletar."
            );
        }
        // Delete the friend from the repository
        this.friendRepository.deleteFriend(friendId);
    }
}

