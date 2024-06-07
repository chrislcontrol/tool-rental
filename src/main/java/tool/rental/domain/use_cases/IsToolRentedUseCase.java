package tool.rental.domain.use_cases;

import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

public class IsToolRentedUseCase {
    private final ToolRepository toolRepository = new ToolRepository();

    public boolean execute(String toolId) throws ToastError {
        return toolRepository.isToolRented(toolId);
    }
}
