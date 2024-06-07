package tool.rental.presentation;

import tool.rental.domain.controllers.UpdateFriendsController;
import tool.rental.domain.entities.Friend;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A frame for updating friend information.
 */
public class FriendUpdateFrame extends PresentationFrame {

    /** The controller for updating friends. */
    private final UpdateFriendsController updateFriendsController = new UpdateFriendsController(this);

    /** Text field for entering social security number. */
    private JTextField social_securityField;

    /** Text field for entering friend's name. */
    private JTextField nameField;

    /** Text field for entering friend's phone number. */
    private JTextField phoneField;

    /** Button to confirm friend update. */
    private JButton confirmButton;

    /** Button to cancel friend update. */
    private JButton cancelButton;

    /** The main panel of the frame. */
    private JPanel MainPanel;

    /** Panel for additional content. */
    private JPanel JPanel;

    /** Another panel for additional content. */
    private JPanel JPanel1;

    /**
     * Constructs a new FriendUpdateFrame for updating friend information.
     *
     * @param friendSelected The friend object whose information is being updated.
     */
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

    /**
     * Sets up the layout of the frame.
     */
    private void setupPageLayout() {
        this.setTitle("Atualizar Amigo");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(30), this.userScreen.heightFraction(30));
        this.setLocationRelativeTo(null);
    }

    /**
     * Sets the main panel of the frame.
     */
    private void setMainPanel() {
        this.setContentPane(this.MainPanel);
    }
}