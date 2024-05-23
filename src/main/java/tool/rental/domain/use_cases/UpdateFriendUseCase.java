package tool.rental.domain.use_cases;

import tool.rental.domain.repositories.FriendRepository;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

public class UpdateFriendUseCase {
    private final ToolRepository toolRepository = new ToolRepository();

    public void execute(String name, int telefone, int identity) throws ToastError {
        if (name == null || name.isEmpty()) {
            throw new ToastError("Nome do amigo não pode ser nulo", "Campo não pode ser nulo");
        }
        if (telefone == 0 ) {
            throw new ToastError("Número de telefone não pode ser zero", "Campo não pode ser zero");
        }
        if (identity == 0 ) {
            throw new ToastError("Número de identidade não pode ser zero", "Campo não pode ser zero");
        }

        this.toolRepository.createTool(brand, name, cost);
    }
}

