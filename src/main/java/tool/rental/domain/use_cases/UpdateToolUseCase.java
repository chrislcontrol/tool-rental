package tool.rental.domain.use_cases;

import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

/**
 * This class represents a use case for updating tool information.
 */
public class UpdateToolUseCase {
    private final ToolRepository toolRepository = new ToolRepository(); // Repository for tools

    /**
     * Executes the use case to update tool information.
     *
     * @param tool  The tool to be updated.
     * @param brand The new brand of the tool.
     * @param name  The new name of the tool.
     * @param cost  The new cost of the tool.
     * @throws ToastError if an error occurs during the execution or if input validation fails.
     */
    public void execute(Tool tool, String brand, String name, double cost) throws ToastError {
        // Validate inputs
        if (brand == null || brand.isEmpty()) {
            throw new ToastError("Marca da ferramenta não pode ser nula.", "Campo não pode ser nulo");
        }
        if (name == null || name.isEmpty()) {
            throw new ToastError("Nome da ferramenta não pode ser nulo.", "Campo não pode ser nulo");
        }
        if (cost <= 0.00) {
            throw new ToastError("Preço da ferramenta não pode ser zero ou valor negativo.", "Campo não pode ser zero ou valor negativo");
        }
        // Check if a tool with the same name and brand already exists
        if (toolRepository.existsByNameAndBrand(name, brand)) {
            throw new ToastError(
                    "Já existe uma ferramenta cadastrada com essa marca e nome.",
                    "Ferramenta já existe"
            );
        }

        // Update tool information in the repository
        this.toolRepository.updateTool(tool, brand, name, cost);
    }
}

