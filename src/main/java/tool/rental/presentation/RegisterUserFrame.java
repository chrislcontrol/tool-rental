package tool.rental.presentation;

import tool.rental.domain.controllers.RegisterUserController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterUserFrame extends PresentationFrame {
    private final RegisterUserController registerUserController = new RegisterUserController(this);

    private JPanel MainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JPasswordField confirmPasswordField;

    public RegisterUserFrame() {
        this.setMainPanel();
        this.setupPageLayout();
        this.setUpListeners();
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    registerUserController.registerUser(
                            usernameField.getText(),
                            new String(passwordField.getPassword()),
                            new String(confirmPasswordField.getPassword())
                    );
                } catch (ToastError ex) {
                    ex.display();
                }
            }
        });
    }

    private void setUpListeners() {
    }

    private void setupPageLayout() {
        this.setTitle("Registrar usuário");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(15), this.userScreen.heightFraction(30));
        this.setLocationRelativeTo(null);
    }

    private void setMainPanel() {
        this.setContentPane(this.MainPanel);
    }
}
