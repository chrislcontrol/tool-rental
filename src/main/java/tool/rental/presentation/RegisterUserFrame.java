package tool.rental.presentation;

import tool.rental.domain.controllers.RegisterUserController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a frame for registering a new user.
 */
public class RegisterUserFrame extends PresentationFrame {

    // Controller instance to manage user registration
    private final RegisterUserController registerUserController = new RegisterUserController(this);

    // Main panel of the frame
    private JPanel MainPanel;

    // Text field for entering the username
    private JTextField usernameField;

    // Password field for entering the password
    private JPasswordField passwordField;

    // Button to confirm user registration
    private JButton confirmButton;

    // Button to cancel user registration
    private JButton cancelButton;

    // Password field for confirming the password
    private JPasswordField confirmPasswordField;

    /**
     * Constructs a new RegisterUserFrame.
     */
    public RegisterUserFrame() {
        this.setMainPanel();
        this.setupPageLayout();
        this.setUpListeners();
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Register the user with the provided information
                    registerUserController.registerUser(
                            usernameField.getText(),
                            new String(passwordField.getPassword()),
                            new String(confirmPasswordField.getPassword())
                    );
                } catch (ToastError ex) {
                    ex.display(); // Display any error that occurs during user registration
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUserController.closeFrame(); // Close the frame upon canceling user registration
            }
        });
    }

    /**
     * Sets up the page layout for the frame.
     */
    private void setupPageLayout() {
        this.setTitle("Registrar usuário"); // Set the title of the frame
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Set close operation
        this.setSize(this.userScreen.widthFraction(15), this.userScreen.heightFraction(30)); // Set size of the frame
        this.setLocationRelativeTo(null); // Center the frame on the screen
    }

    /**
     * Sets the main panel of the frame.
     */
    private void setMainPanel() {
        this.setContentPane(this.MainPanel); // Set the main panel of the frame
    }

    /**
     * Sets up listeners for the frame components.
     */
    private void setUpListeners() {
        // No additional listeners set up for this frame
    }
}

