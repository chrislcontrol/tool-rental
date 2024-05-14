package tool.rental.presentation;

import tool.rental.domain.controllers.AppMainController;
import tool.rental.utils.PresentationFrame;

import javax.swing.*;

public class FriendsScreenFrame extends PresentationFrame {
    private final AppMainController controller = new AppMainController(this);
    private JPanel mainPanel;
    private JTable friendsTable;
    private JScrollPane JScrollPanel;
    private JPanel Panel1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton registerFriendButton;
    private JButton exitButton;
    private JButton rankingButton;


    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
