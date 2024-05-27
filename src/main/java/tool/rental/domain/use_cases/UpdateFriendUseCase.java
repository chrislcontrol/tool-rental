package tool.rental.domain.use_cases;

import tool.rental.domain.entities.User;
import tool.rental.domain.repositories.FriendRepository;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

public class UpdateFriendUseCase {
    private final FriendRepository friendRepository = new FriendRepository();

    public void execute(String id,String name, String phone, String social_security) throws ToastError {

        if (id == null)
            throw  new ToastError("ID do amigo não podde ser nulo", "Campo não poder nulo");

        if (name == null || name.isEmpty()) {
            throw new ToastError("Nome do amigo não pode ser nulo", "Campo não pode ser nulo");
        }
        if (phone == null ) {
            throw new ToastError("Número de telefone não pode ser nulo", "Campo não pode ser nulo");
        }
        if (social_security == null ) {
            throw new ToastError("Número de identidade não pode ser nulo", "Campo não pode ser nulo");
        }

        this.friendRepository.updateFriend(id,name, phone, social_security);
    }
}

