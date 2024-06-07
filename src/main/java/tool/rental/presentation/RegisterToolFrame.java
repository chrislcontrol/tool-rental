package tool.rental.presentation;

import tool.rental.domain.controllers.RegisterToolController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a frame for registering a tool.
 */
public class RegisterToolFrame extends PresentationFrame {

    /**
     * The controller for registering a tool.
     */
    private final RegisterToolController registerToolController = new RegisterToolController(this);

    /**
     * The text field for entering the cost of the tool.
     */
    private JTextField costField;
    /**
     * The text field for entering the brand of the tool.
     */
    private JTextField brandField;
    /**
     * The button for confirming the registration of the tool.
     */
    private JButton confirmButton;
    /**
     * The button for canceling the registration of the tool.
     */
    private JButton cancelButton;
    /**
     * The main panel of the frame.
     */
    private JPanel MainPanel;
    /**
     * The text field for entering the name of the tool.
     */
    private JTextField nameField;


    /**
     * Constructs a new RegisterToolFrame with a success callback.
     *
     * @param successCallback the callback to be executed when the tool is successfully registered
     */
    public RegisterToolFrame(Runnable successCallback) {
        this.setMainPanel();
        this.setupPageLayout();
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String costText = costField.getText().replace(',', '.');
                    registerToolController.registerTool(
                            brandField.getText(),
                            nameField.getText(),
                            Double.parseDouble(costText),
                            successCallback

                    );
                } catch (ToastError ex) {
                    ex.display();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerToolController.closeFrame();
            }
        });
    }

    /**
     * Sets up the layout of the page.
     */
    private void setupPageLayout() {
        this.setTitle("Registrar ferramenta");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(30), this.userScreen.heightFraction(30));
        this.setLocationRelativeTo(null);
    }

    /**
     * Sets the main panel of the frame.
     */
    private void setMainPanel() {
        this.setContentPane(this.MainPanel);
    }

    /**
     * Creates the UI components.
     */
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

