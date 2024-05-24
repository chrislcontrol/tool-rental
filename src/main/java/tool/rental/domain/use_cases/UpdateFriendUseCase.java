package tool.rental.domain.use_cases;

import tool.rental.domain.repositories.FriendRepository;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

public class UpdateFriendUseCase {
    private final FriendRepository friendRepository = new FriendRepository();

    public void execute(String name, int phone, int socialSecurity) throws ToastError {

        if (name == null || name.isEmpty()) {
            throw new ToastError("Nome do amigo não pode ser nulo", "Campo não pode ser nulo");
        }
        if (phone == 0 ) {
            throw new ToastError("Número de telefone não pode ser zero", "Campo não pode ser zero");
        }
        if (social_security == 0 ) {
            throw new ToastError("Número de identidade não pode ser zero", "Campo não pode ser zero");
        }

        this.friendRepository.updateFriend(name, phone, social_security);
    }
}

