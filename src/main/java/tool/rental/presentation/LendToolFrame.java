package tool.rental.presentation;

import tool.rental.app.Settings;
import tool.rental.domain.controllers.LendToolFrameController;
import tool.rental.domain.entities.Friend;
import tool.rental.utils.PresentationFrame;
import tool.rental.utils.ToastError;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LendToolFrame extends PresentationFrame {
    private final LendToolFrameController lendToolFrameController = new LendToolFrameController(this);
    private JPanel mainPanel;
    private JComboBox friendList;
    private JButton rentButton;
    private JButton cancelButton;
    private JLabel toolName;
    private String toolId;
    public LendToolFrame(String toolId) throws ToastError {
        this.toolId = toolId;
        this.setMainPanel();
        this.setupPageLayout();
        this.setUpListeners();
        this.loadData();
        this.setPointer(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
                this.rentButton,
                this.cancelButton,
                this.friendList
                );
    }

    private void setupPageLayout() {
        this.setTitle(String.format("Empréstimo de ferramenta (%s) ", Settings.getUser().getUsername()));
        this.setDefaultCloseOperation(LendToolFrame.DISPOSE_ON_CLOSE);
        this.setSize(this.userScreen.widthFraction(30), this.userScreen.heightFraction(30));
        this.setLocationRelativeTo(null);
    }

    private void setMainPanel() {
        this.setContentPane(this.mainPanel);
    }

    public void setUpListeners(){
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LendToolFrame.this.dispose();
            }
        });

        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void setupComboBox() throws ToastError {
        this.loadData();
    }
    private void loadData() throws ToastError {
        DefaultComboBoxModel<Friend> model = new DefaultComboBoxModel<>();
        friendList.setModel(model);
        ArrayList<Friend> friendRows = this.lendToolFrameController.listFriendsAsComboBox();
        friendList.addItem("Selecione");
        for(Friend friendRow : friendRows) {
            friendList.addItem(friendRow.getName());
        }
    }

    private void setPointer(Cursor cursor, JComponent... components) {
        for (JComponent component : components) {
            component.setCursor(cursor);
        }
    }

}
