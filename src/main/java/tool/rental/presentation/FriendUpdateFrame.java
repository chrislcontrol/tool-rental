package tool.rental.presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FriendUpdateFrame {
    private JTextField identityField;
    private JTextField nameField;
    private JTextField telefoneField;
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
                    registerToolController.registerTool(
                            nameField.getText(),
                            Integer.parseInt(telefoneField.getText()),
                            Integer.parseInt(identityField.getText()),

    }
}
