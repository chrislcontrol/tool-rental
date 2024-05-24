package tool.rental.presentation;

import tool.rental.domain.controllers.RegisterFriendController;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFriendFrame extends PresentationFrame {

    private JPanel MainPanel;
    private JButton cancelButton;
    private JButton confirmButton;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField social_securityField;

    private final RegisterFriendController registerFriendController = new RegisterFriendController(this);

    public RegisterFriendFrame(Runnable successCallback) {
        this.setMainPanel();
        this.setupPageLayout();
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    registerFriendController.registerFriend(
                            nameField.getText(),
                            phoneField.getText(),
                            social_securityField.getText(),
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
                registerFriendController.closeFrame();
            }
        });
    }

    private void setupPageLayout() {
        this.setTitle("Registrar amigo");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(30), this.userScreen.heightFraction(30));
        this.setLocationRelativeTo(null);
    }

    private void setMainPanel() {
        this.setContentPane(this.MainPanel);
    }
}

