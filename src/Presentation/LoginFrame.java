package tool.rental.Presentation;

import tool.rental.Domain.Controllers.LoginController;
import tool.rental.Utils.PresentationFrame;
import tool.rental.Utils.ToastError;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends PresentationFrame {
    private final LoginController loginController = new LoginController(this);
    private JPanel MainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JCheckBox rememberMeCheckBox;


    public LoginFrame() {
        this.setupPageLayout();
        this.setMainPanel();
        this.setUpListeners();
    }

    private void setUpListeners() {
        this.loginButtonActionListeners();

    }

    private void loginButtonActionListeners() {
        this.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    loginController.execute(
                            usernameField.getText(),
                            new String(passwordField.getPassword()),
                            rememberMeCheckBox.isSelected()
                    );
                } catch (ToastError ex) {
                    ex.display();
                }
            }
        });
    }

    private void setMainPanel() {
        this.setContentPane(this.MainPanel);
    }

    private void setupPageLayout() {
        this.setTitle("Faça seu login");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(20), this.userScreen.heightFraction(40));
        this.setLocationRelativeTo(null);
    }
}
