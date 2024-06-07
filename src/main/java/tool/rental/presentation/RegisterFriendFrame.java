package tool.rental.presentation;

import tool.rental.domain.controllers.RegisterFriendController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a frame for registering a new friend.
 */
public class RegisterFriendFrame extends PresentationFrame {

    // Main panel of the frame
    private JPanel MainPanel;

    // Button to cancel friend registration
    private JButton cancelButton;

    // Button to confirm friend registration
    private JButton confirmButton;

    // Text field for entering the name of the friend
    private JTextField nameField;

    // Text field for entering the phone number of the friend
    private JTextField phoneField;

    // Text field for entering the social security number of the friend
    private JTextField social_securityField;

    // Controller instance to manage friend registration
    private final RegisterFriendController registerFriendController = new RegisterFriendController(this);

    /**
     * Constructs a new RegisterFriendFrame.
     *
     * @param successCallback The callback to be executed upon successful friend registration
     */
    public RegisterFriendFrame(Runnable successCallback) {
        this.setMainPanel();
        this.setupPageLayout();
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Validate social security number length
                    if (social_securityField.getText().length() != 11) {
                        throw new ToastError(
                                "Não foi possível cadastrar amigo pois a identidade deve possuir 11 caracteres",
                                "Entrada de dados em formato inválido"
                        );
                    }
                    // Register the friend with the provided information
                    registerFriendController.registerFriend(
                            nameField.getText(),
                            phoneField.getText(),
                            social_securityField.getText(),
                            successCallback
                    );
                } catch (ToastError ex) {
                    ex.display(); // Display any error that occurs during friend registration
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerFriendController.closeFrame(); // Close the frame upon canceling friend registration
            }
        });
    }

    /**
     * Sets up the page layout for the frame.
     */
    private void setupPageLayout() {
        this.setTitle("Registrar amigo"); // Set the title of the frame
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Set close operation
        this.setSize(this.userScreen.widthFraction(30), this.userScreen.heightFraction(30)); // Set size of the frame
        this.setLocationRelativeTo(null); // Center the frame on the screen
    }

    /**
     * Sets the main panel of the frame.
     */
    private void setMainPanel() {
        this.setContentPane(this.MainPanel); // Set the main panel of the frame
    }
}


