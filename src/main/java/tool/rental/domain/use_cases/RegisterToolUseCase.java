package tool.rental.domain.use_cases;

import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

public class RegisterToolUseCase {

    private final ToolRepository toolRepository = new ToolRepository();

    public void execute(String brand, double cost) throws ToastError {


        if (brand == null || brand.isEmpty()) {
            throw new ToastError("Nome da marca da ferramenta não pode ser nulo", "Campo não pode ser nulo");
        }
        if (cost == 0.0) {
            throw new ToastError("Preço da ferramenta não pode ser zero", "Campo não pode ser zero");
        }
        this.toolRepository.createTool( brand, cost);
    }
}
