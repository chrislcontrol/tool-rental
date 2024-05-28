package tool.rental.domain.use_cases;

import tool.rental.domain.repositories.FriendRepository;
import tool.rental.utils.ToastError;
import tool.rental.domain.entities.User;

public class UpdateFriendUseCase {
    private final FriendRepository friendRepository = new FriendRepository();

    public void execute(String id,String name, String phone, String social_security,User user) throws ToastError {
        if (name == null || name.isEmpty()) {
            throw new ToastError("Nome do amigo não pode ser nulo", "Campo não pode ser nulo");
        }
        if (phone == null ) {
            throw new ToastError("Número de telefone não pode ser nulo", "Campo não pode ser nulo");
        }
        if (social_security == null ) {
            throw new ToastError("Número de identidade não pode ser nulo", "Campo não pode ser nulo");
        }
        boolean exists = this.friendRepository.getFriendBySocial_Security(social_security);

        if (exists) {
            throw new ToastError("Amigo já existe com essa identidade.", "Amigo já cadastrado.");
        }

        this.friendRepository.updateFriend(id, name, phone, social_security, user);
    }
}