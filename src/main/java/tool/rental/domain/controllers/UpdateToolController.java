package tool.rental.domain.controllers;

import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.domain.use_cases.UpdateToolUseCase;
import tool.rental.utils.Controller;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;
import javax.swing.JOptionPane;

public class UpdateToolController extends Controller {

    private final UpdateToolUseCase updateToolUseCase = new UpdateToolUseCase();
    private final ToolRepository toolRepository = new ToolRepository();

    public UpdateToolController(PresentationFrame frame) {
        super(frame);
    }

    public void updateTool(String toolId, String brand, String name, double cost, Runnable callback) throws ToastError {
        Tool tool = toolRepository.getById(toolId);
        this.updateToolUseCase.execute(tool, brand, name, cost);
        JOptionPane.showMessageDialog(null, "Ferramenta atualizada com sucesso!");

        callback.run();

        this.closeFrame();
    }

    public void closeFrame() {
        frame.setVisible(false);
    }
}
