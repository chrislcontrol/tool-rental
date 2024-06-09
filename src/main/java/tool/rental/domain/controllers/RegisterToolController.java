
package tool.rental.domain.controllers;

import tool.rental.domain.use_cases.RegisterToolUseCase;
import tool.rental.utils.Controller;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;
import javax.swing.JOptionPane;

/**
 * RegisterToolController is a controller class that handles the registration of tools in the tool rental application.
 */
public class RegisterToolController extends Controller {

    /**
     * Private final field to hold an instance of RegisterToolUseCase.
     */
    private final RegisterToolUseCase registerToolUseCase = new RegisterToolUseCase();

    /**
     * Constructor that takes a PresentationFrame object as a parameter.
     * 
     * @param frame the presentation frame for the tool rental application
     */
    public RegisterToolController(PresentationFrame frame) {
        /**
         * Call the superclass constructor with the frame.
         */
        super(frame);
    }

    /**
     * Method to register a tool with the provided brand, name, and cost.
     * 
     * @param brand the brand of the tool
     * @param name the name of the tool
     * @param cost the cost of the tool
     * @param callback the callback to run after the tool has been registered
     * @throws ToastError if an error occurs during the registration process
     */
    public void registerTool(String brand, String name, double cost, Runnable callback) throws ToastError {
        /**
         * Execute the register tool use case with the provided brand, name, and cost.
         */
        this.registerToolUseCase.execute(brand, name, cost);

        /**
         * Display a success message using JOptionPane.
         */
        JOptionPane.showMessageDialog(null, "Ferramenta cadastrada com sucesso!");

        /**
         * Run the provided callback.
         */
        callback.run();

        /**
         * Close the current frame.
         */
        closeFrame();
    }

    /**
     * Method to close the current frame.
     */
    public void closeFrame() {
        /**
         * Set the visibility of the frame to false.
         */
        frame.setVisible(false);
    }
}