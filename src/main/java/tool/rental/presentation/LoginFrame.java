package tool.rental.presentation;

import tool.rental.domain.controllers.LoginController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a frame for user login.
 */
public class LoginFrame extends PresentationFrame {

    // Controller instance to manage the login process
    private final LoginController loginController = new LoginController(this);

    // Main panel of the frame
    private JPanel MainPanel;

    // Text field for entering the username
    private JTextField usernameField;

    // Password field for entering the password
    private JPasswordField passwordField;

    // Button to initiate the login process
    private JButton loginButton;

    // Checkbox for remembering the login credentials
    private JCheckBox rememberMeCheckBox;

    // Button to register a new user
    private JButton registerButton;

    /**
     * Constructs a new LoginFrame.
     */
    public LoginFrame() {
        this.setMainPanel();
        this.setupPageLayout();
        this.setUpListeners();
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginController.openRegisterModal(); // Open the register modal upon button click
            }
        });
    }

    /**
     * Sets up listeners for UI components.
     */
    protected void setUpListeners() {
        this.loginButtonActionListeners();
    }

    /**
     * Sets up action listeners for the login button.
     */
    private void loginButtonActionListeners() {
        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Perform login using the provided credentials
                    loginController.login(
                            usernameField.getText(),
                            new String(passwordField.getPassword()),
                            rememberMeCheckBox.isSelected()
                    );
                } catch (ToastError ex) {
                    ex.display(); // Display any error that occurs during login
                }
            }
        });
    }

    /**
     * Sets up the page layout for the frame.
     */
    private void setupPageLayout() {
        this.setTitle("Faça seu login"); // Set the title of the frame
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // Set close operation
        this.setSize(this.userScreen.widthFraction(30), this.userScreen.heightFraction(40)); // Set size of the frame
        this.setLocationRelativeTo(null); // Center the frame on the screen
    }

    /**
     * Sets the main panel of the frame.
     */
    private void setMainPanel() {
        this.setContentPane(this.MainPanel); // Set the main panel of the frame
    }
}

