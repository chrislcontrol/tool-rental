package tool.rental.domain.use_cases;

import tool.rental.domain.repositories.FriendRepository;
import tool.rental.utils.ToastError;

/**
 * This class represents a use case for registering a new friend in the system.
 */
public class RegisterFriendUseCase {
    private final FriendRepository friendRepository = new FriendRepository(); // Repository for friends

    /**
     * Executes the use case to register a new friend in the system.
     *
     * @param name            The name of the friend.
     * @param phone           The phone number of the friend.
     * @param social_security The social security number of the friend.
     * @throws ToastError if an error occurs during the execution or if input validation fails.
     */
    public void execute(String name, String phone, String social_security) throws ToastError {
        // Validate inputs
        if (name == null || name.isEmpty()) {
            throw new ToastError("Nome não pode ser nulo", "Campo não pode ser nulo");
        }
        if (phone == null || phone.isEmpty()) {
            throw new ToastError("Telefone não pode ser nulo", "Campo não pode ser nulo");
        }
        if (social_security == null || social_security.isEmpty()) {
            throw new ToastError("Identidade não pode ser nulo", "Campo não pode ser nulo");
        }

        // Check if a friend with the same name and social security already exists
        if (friendRepository.existsByNameAndSocial_Security(name, social_security)) {
            throw new ToastError(
                    "Já existe uma pessoa cadastrada com este nome e identidade",
                    "Ferramenta já existe"
            );
        }

        // Create the friend in the repository
        this.friendRepository.createFriend(name, phone, social_security);
    }
}

