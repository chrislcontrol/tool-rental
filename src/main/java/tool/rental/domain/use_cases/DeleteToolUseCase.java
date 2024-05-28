package tool.rental.domain.use_cases;

import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.utils.ToastError;

public class DeleteToolUseCase {
    ToolRepository toolRepository = new ToolRepository();

    public void execute(Tool tool) throws ToastError {
        this.toolRepository.deleteTool(tool);
    }
}
