package tool.rental.domain.use_cases;

import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

public class UpdateToolUseCase {
    private final ToolRepository toolRepository = new ToolRepository();

    public void execute(Tool tool, String brand, String name, double cost) throws ToastError {
        if (brand == null || brand.isEmpty()) {
            throw new ToastError("Marca da ferramenta não pode ser nulo.", "Campo não pode ser nulo"
            );
        }
        if (name == null || name.isEmpty()) {
            throw new ToastError("Nome da ferramenta não pode ser nulo.", "Campo não pode ser nulo"
            );
        }
        if (cost <= 0.00) {
            throw new ToastError("Preço da ferramenta não pode ser zero ou valor negativo.", "Campo não pode ser zero ou valor negativo"
            );
        }
        if (toolRepository.existsByNameAndBrand(name, brand)) {
            throw new ToastError(
                    "Já existe uma ferramenta cadastra com essa marca e nome.",
                    "Ferramenta já existe"
            );
        }

        this.toolRepository.updateTool(tool, brand, name, cost);
    }
}
