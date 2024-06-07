package tool.rental.domain.controllers;

import tool.rental.domain.entities.Tool;
import tool.rental.domain.repositories.ToolRepository;
import tool.rental.domain.use_cases.UpdateToolUseCase;
import tool.rental.utils.Controller;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;
import javax.swing.JOptionPane;

/**
 * The controller for updating a tool.
 */
public class UpdateToolController extends Controller {

    /**
     * The use case to update a tool.
     */
    private final UpdateToolUseCase updateToolUseCase = new UpdateToolUseCase();

    /**
     * The repository for tools.
     */
    private final ToolRepository toolRepository = new ToolRepository();

    /**
     * Creates a new instance of the UpdateToolController.
     *
     * @param frame the presentation frame
     */
    public UpdateToolController(PresentationFrame frame) {
        super(frame);
    }

    /**
     * Updates a tool.
     *
     * @param toolId the id of the tool to update
     * @param brand the new brand of the tool
     * @param name the new name of the tool
     * @param cost the new cost of the tool
     * @param callback a callback to be executed after the update
     * @throws ToastError if an error occurs
     */
    public void updateTool(String toolId, String brand, String name, double cost, Runnable callback) throws ToastError {
        Tool tool = toolRepository.getById(toolId);
        this.updateToolUseCase.execute(tool, brand, name, cost);
        JOptionPane.showMessageDialog(null, "Ferramenta atualizada com sucesso!");

        callback.run();

        this.closeFrame();
    }

    /**
     * Closes the frame.
     */
    public void closeFrame() {
        frame.setVisible(false);
    }
}
