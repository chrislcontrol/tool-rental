package tool.rental.presentation;

import tool.rental.domain.controllers.AppMainController;
import tool.rental.utils.PresentationFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendsScreenFrame extends PresentationFrame {
    private JTable friendsTable;
    private JScrollPane JScrollPanel;
    private JPanel Panel1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton registerFriendButton;
    private JButton exitButton;
    private JButton rankingButton;
    private JPanel MainPanel;



    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public FriendsScreenFrame() {
        this.setupPageLayout();
        this.setMainPanel();
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void setupPageLayout() {
        this.setTitle("Amigos");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(20), this.userScreen.heightFraction(40));
        this.setLocationRelativeTo(null);
    }

    private void setMainPanel() {
        this.setContentPane(this.MainPanel);
    }
}
