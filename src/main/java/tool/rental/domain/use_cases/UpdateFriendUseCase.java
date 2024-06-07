package tool.rental.domain.use_cases;

import tool.rental.domain.entities.User;
import tool.rental.utils.ToastError;

public class UpdateFriendUseCase {
    private final FriendRepository friendRepository = new FriendRepository();

    public void execute(String id, String name, String phone, String social_security, User user) throws ToastError {
        if (name == null || name.isEmpty()) {
            throw new ToastError("Nome do amigo não pode ser nulo", "Campo não pode ser nulo");
        }
        if (phone == null) {
            throw new ToastError("Número de telefone não pode ser nulo", "Campo não pode ser nulo");
        }
        if (social_security == null) {
            throw new ToastError("Número de identidade não pode ser nulo", "Campo não pode ser nulo");
        }
        if (!social_security.matches("\\d{11}")) {
            throw new ToastError("Só podem ser informados números com 11 caractéres", "Campo deve ter apenas números de 11 caractéres");
        }
        boolean exists = this.friendRepository.existsByNameAndSocial_Security(name, social_security);

        if (exists) {
            throw new ToastError("Amigo já existe com essa identidade.", "Amigo já cadastrado.");
        }

        this.friendRepository.updateFriend(id, name, phone, social_security, user);
    }
}