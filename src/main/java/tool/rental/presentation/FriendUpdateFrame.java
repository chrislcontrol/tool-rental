package tool.rental.presentation;

import tool.rental.domain.controllers.UpdateFriendsController;
import tool.rental.domain.entities.Friend;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendUpdateFrame extends PresentationFrame {
    private final UpdateFriendsController updateFriendsController = new UpdateFriendsController(this);
    private JTextField social_securityField;
    private JTextField nameField;
    private JTextField phoneField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JPanel MainPanel;
    private JPanel JPanel;
    private JPanel JPanel1;

    public FriendUpdateFrame(Friend friendSelected) {
        this.setMainPanel();
        this.setupPageLayout();
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateFriendsController.updateFriend(
                            friendSelected.getId(),
                            nameField.getText(),
                            phoneField.getText(),
                            social_securityField.getText(),
                            friendSelected.getUser()
                    );
                } catch (ToastError ex) {
                    ex.display();
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFriendsController.closeFrame();
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