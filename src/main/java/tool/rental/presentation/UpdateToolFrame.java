package tool.rental.presentation;

import tool.rental.domain.controllers.UpdateToolController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a frame for updating tool information.
 */
public class UpdateToolFrame extends PresentationFrame {

    // Controller instance to manage tool updates
    private final UpdateToolController updateToolController = new UpdateToolController(this);

    // Main panel of the frame
    private JPanel MainPanel;

    // Text field for entering tool brand
    private JTextField brandField;

    // Text field for entering tool name
    private JTextField nameField;

    // Text field for entering tool cost
    private JTextField costField;

    // Button to confirm tool update
    private JButton confirmButton;

    // Button to cancel tool update
    private JButton cancelButton;

    // ID of the tool being updated
    private final String toolId;

    // Callback function to execute upon successful update
    private final Runnable successCallback;

    /**
     * Constructs a new UpdateToolFrame.
     *
     * @param toolId          the ID of the tool being updated
     * @param successCallback a callback function to execute upon successful update
     * @throws ToastError if an error occurs while setting up the frame
     */
    public UpdateToolFrame(String toolId, Runnable successCallback) throws ToastError {
        this.toolId = toolId;
        this.successCallback = successCallback;
        this.setMainPanel();
        this.setupPageLayout();
        this.setupListeners();
        this.setPointer(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
                this.cancelButton,
                this.confirmButton
        );
    }

    /**
     * Sets up action listeners for buttons.
     */
    protected void setupListeners() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (costField.getText() == null || costField.getText().isEmpty()) {
                        throw new ToastError("Preço não pode ser nulo.", "Campo não pode ser nulo");
                    }
                    String costText = costField.getText().replace(",", ".");
                    updateToolController.updateTool(
                            toolId,
                            brandField.getText(),
                            nameField.getText(),
                            Double.parseDouble(costText),
                            successCallback
                    );

                } catch (ToastError exc) {
                    exc.display();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateToolController.closeFrame();
            }
        });
    }

    /**
     * Sets the main panel of the frame.
     */
    private void setMainPanel() {
        this.setContentPane(this.MainPanel);
    }

    /**
     * Sets the cursor type for specified components.
     *
     * @param cursor     the cursor type to set
     * @param components the components to set the cursor for
     */
    private void setPointer(Cursor cursor, JComponent... components) {
        for (JComponent component : components) {
            component.setCursor(cursor);
        }
    }

    /**
     * Sets up the page layout for the frame.
     */
    private void setupPageLayout() {
        this.setTitle("Atualizar ferramenta"); // Set the title of the frame
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Set close operation
        this.setSize(this.userScreen.widthFraction(30), this.userScreen.heightFraction(30)); // Set size of the frame
        this.setLocationRelativeTo(null); // Center the frame on the screen
    }
}
