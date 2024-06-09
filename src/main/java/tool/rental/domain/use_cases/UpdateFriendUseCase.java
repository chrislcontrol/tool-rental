package tool.rental.domain.use_cases;

import tool.rental.domain.entities.User;
import tool.rental.domain.repositories.FriendRepository;
import tool.rental.utils.ToastError;

/**
 * This class represents a use case for updating friend information.
 */
public class UpdateFriendUseCase {
    private final FriendRepository friendRepository = new FriendRepository(); // Repository for friends

    /**
     * Executes the use case to update friend information.
     *
     * @param id             The ID of the friend to be updated.
     * @param name           The new name of the friend.
     * @param phone          The new phone number of the friend.
     * @param social_security The new social security number of the friend.
     * @param user           The user performing the update operation.
     * @throws ToastError if an error occurs during the execution or if input validation fails.
     */
    public void execute(String id, String name, String phone, String social_security, User user) throws ToastError {
        // Validate inputs
        if (name == null || name.isEmpty()) {
            throw new ToastError("Nome do amigo não pode ser nulo", "Campo não pode ser nulo");
        }
        if (phone == null) {
            throw new ToastError("Número de telefone não pode ser nulo", "Campo não pode ser nulo");
        }
        if (social_security == null) {
            throw new ToastError("Número de identidade não pode ser nulo", "Campo não pode ser nulo");
        }
        // Validate social security number format
        if (!social_security.matches("\\d{11}")) {
            throw new ToastError("Só podem ser informados números com 11 caracteres", "Campo deve ter apenas números de 11 caracteres");
        }
        // Check if a friend with the same name and social security already exists
        boolean exists = this.friendRepository.existsByNameAndSocial_Security(name, social_security);
        if (exists) {
            throw new ToastError("Amigo já existe com essa identidade.", "Amigo já cadastrado.");
        }

        // Update friend information in the repository
        this.friendRepository.updateFriend(id, name, phone, social_security, user);
    }
}
