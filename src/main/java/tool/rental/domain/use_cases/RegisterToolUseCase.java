package tool.rental.domain.use_cases;

import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

/**
 * Represents a use case for registering a tool.
 */
public class RegisterToolUseCase {

    /**
     * The tool repository used to interact with the tool database.
     */
    private final ToolRepository toolRepository = new ToolRepository();

    /**
     * Executes the use case for registering a tool.
     *
     * @param brand the brand of the tool
     * @param name the name of the tool
     * @param cost the cost of the tool
     * @throws ToastError if an error occurs while registering the tool
     */
    public void execute(String brand, String name, double cost) throws ToastError {
        if (brand == null || brand.isEmpty()) {
            throw new ToastError("Nome da marca da ferramenta não pode ser nulo", "Campo não pode ser nulo");
        }
        if (name == null || name.isEmpty()) {
            throw new ToastError("Nome ferramenta não pode ser nulo", "Campo não pode ser nulo");
        }
        if (cost == 0.0) {
            throw new ToastError("Preço da ferramenta não pode ser zero", "Campo não pode ser zero");
        }

        if (toolRepository.existsByNameAndBrand(name, brand)) {
            throw new ToastError(
                    "Já existe uma ferramenta cadastrada com esta marca e nome",
                    "Ferramenta já existe"
            );
        }

        this.toolRepository.createTool(brand, name, cost);
    }
}
