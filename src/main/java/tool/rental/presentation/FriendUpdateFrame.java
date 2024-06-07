package tool.rental.presentation;

import tool.rental.domain.controllers.UpdateFriendsController;
import tool.rental.domain.entities.Friend;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Represents a frame for updating friend information.
 */
public class FriendUpdateFrame extends PresentationFrame {

    // Controller instance to manage friend updates
    private final UpdateFriendsController updateFriendsController = new UpdateFriendsController(this);

    // Text field for entering the friend's social security number
    private JTextField social_securityField;

    // Text field for entering the friend's name
    private JTextField nameField;

    // Text field for entering the friend's phone number
    private JTextField phoneField;

    // Button to confirm the friend update
    private JButton confirmButton;

    // Button to cancel the friend update
    private JButton cancelButton;

    // Main panel of the frame
    private JPanel mainPanel;

    // Panel of the frame
    private JPanel jPanel;

    /**
     * Constructs a new FriendUpdateFrame.
     *
     * @param friendSelected The friend whose information is being updated
     */
    public FriendUpdateFrame(Friend friendSelected) {
        this.setMainPanel();
        this.setupPageLayout();

        // Action listener for the confirm button
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Update the friend's information
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

        // Action listener for the cancel button
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the frame
                updateFriendsController.closeFrame();
            }
        });
    }

    /**
     * Sets up the page layout for the frame.
     */
    private void setupPageLayout() {
        this.setTitle("Atualizar Amigo"); // Set the title of the frame
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Set close operation
        this.setSize(this.userScreen.widthFraction(30), this.userScreen.heightFraction(30)); // Set size of the frame
        this.setLocationRelativeTo(null); // Center the frame on the screen
    }

    /**
     * Sets the main panel of the frame.
     */
    private void setMainPanel() {
        this.setContentPane(this.mainPanel); // Set the main panel of the frame
    }
}