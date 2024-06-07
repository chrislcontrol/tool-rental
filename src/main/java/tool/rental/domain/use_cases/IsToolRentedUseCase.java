package tool.rental.domain.use_cases;

import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

/**
 * This class represents a use case for checking if a tool is currently rented.
 */
public class IsToolRentedUseCase {
    private final ToolRepository toolRepository = new ToolRepository(); // Repository for tools

    /**
     * Executes the use case to check if a tool is currently rented.
     *
     * @param toolId The ID of the tool to check.
     * @return true if the tool is rented, false otherwise.
     * @throws ToastError if an error occurs during the execution.
     */
    public boolean execute(String toolId) throws ToastError {
        // Check if the tool is currently rented
        return toolRepository.isToolRented(toolId);
    }
}

