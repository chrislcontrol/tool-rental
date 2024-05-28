package tool.rental.presentation;

import tool.rental.domain.controllers.UpdateFriendsController;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendUpdateFrame {
    private JTextField social_securityField;
    private JTextField nameField;
    private JTextField phoneField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JPanel JPanel;
    private JPanel MainPanel;

    public FriendUpdateFrame() {
        this.setMainPanel();
        this.setupPageLayout();
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UpdateFriendsController.updateFriend(
                            nameField.getText(),
                            Integer.parseInt(phoneField.getText()),
                            Integer.parseInt(social_securityField.getText())


                            );
                } catch (ToastError ex) {
                    ex.display();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateFriendsController.closeFrame();
            }
        });
    }

    private void setupPageLayout() {
        this.setTitle("Atualizar Amigo");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(30), this.userScreen.heightFraction(30));
        this.setLocationRelativeTo(null);
    }

    private void setMainPanel() {
        this.setContentPane(this.MainPanel);
    }
}