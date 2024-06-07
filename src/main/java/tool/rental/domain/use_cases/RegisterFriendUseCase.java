package tool.rental.domain.use_cases;

import tool.rental.domain.repositories.FriendRepository;
import tool.rental.utils.ToastError;

public class RegisterFriendUseCase {

    private final FriendRepository friendRepository = new FriendRepository();

    public void execute(String name, String phone, String social_security) throws ToastError {
        if (name == null || name.isEmpty()) {
            throw new ToastError("Nome não pode ser nulo", "Campo não pode ser nulo");
        }
        if (phone == null || phone.isEmpty()) {
            throw new ToastError("Telefone não pode ser nulo", "Campo não pode ser nulo");
        }
        if (social_security == null || social_security.isEmpty()) {
            throw new ToastError("Identidade não pode ser nulo", "Campo não pode ser nulo");
        }

        if (friendRepository.existsByNameAndSocial_Security(name, social_security)) {
            throw new ToastError(
                    "Já existe uma pessoa cadastrada com este nome e identidade",
                    "Ferramenta já existe"
            );
        }

        this.friendRepository.createFriend(name, phone, social_security);
    }
}
