package tool.rental.presentation;

import tool.rental.domain.controllers.UpdateFriendsController;

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
                            Integer.parseInt(social_securityField.getText()),

    }
}
