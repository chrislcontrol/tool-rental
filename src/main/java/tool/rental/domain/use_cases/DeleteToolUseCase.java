package tool.rental.domain.use_cases;

import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

/**
 * This class represents a use case for deleting a tool from the system.
 */
public class DeleteToolUseCase {
    private final ToolRepository toolRepository = new ToolRepository(); // Repository for tools

    /**
     * Executes the use case to delete a tool from the system.
     *
     * @param tool The tool to be deleted.
     * @throws ToastError if an error occurs during the execution.
     */
    public void execute(Tool tool) throws ToastError {
        // Delete the tool from the repository
        this.toolRepository.deleteTool(tool);
    }
}

